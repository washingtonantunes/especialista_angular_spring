package br.com.wti.algamoney.api.resource;

import br.com.wti.algamoney.api.event.RecursoCriadoEvent;
import br.com.wti.algamoney.api.exception.AlgamoneyExceptionHandler.Erro;
import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.repository.LancamentoRepository;
import br.com.wti.algamoney.api.repository.filter.LancamentoFilter;
import br.com.wti.algamoney.api.service.LancamentoService;
import br.com.wti.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Washington Antunes for wTI on 20/07/2023
 */
@RestController
@RequestMapping("lancamentos")
public class LancamentoResource {

  @Autowired
  private LancamentoRepository lancamentoRepository;

  @Autowired
  private LancamentoService lancamentoService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;

  @GetMapping
  public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable) {
    return lancamentoRepository.filtrar(lancamentoFilter, pageable);
  }

  @PostMapping
  public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
    Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
  }

  @GetMapping("{codigo}")
  public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
    return lancamentoRepository.findById(codigo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    lancamentoRepository.deleteById(codigo);
  }

  @ExceptionHandler({PessoaInexistenteOuInativaException.class})
  public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
    String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
    String mensagemDesenvolvedor = ex.toString();
    List<Erro> erros = List.of(new Erro(mensagemUsuario, mensagemDesenvolvedor));
    return ResponseEntity.badRequest().body(erros);
  }
}
