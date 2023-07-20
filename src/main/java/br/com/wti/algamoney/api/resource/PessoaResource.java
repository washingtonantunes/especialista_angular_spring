package br.com.wti.algamoney.api.resource;

import br.com.wti.algamoney.api.event.RecursoCriadoEvent;
import br.com.wti.algamoney.api.model.Pessoa;
import br.com.wti.algamoney.api.repository.PessoaRepository;
import java.net.URI;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Washington Antunes for wTI on 19/07/2023
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @PostMapping
  public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
    Pessoa pessoaSalva = pessoaRepository.save(pessoa);

    publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
    return pessoaRepository.findById(codigo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
