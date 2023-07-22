package br.com.wti.algamoney.api.service;

import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.model.Pessoa;
import br.com.wti.algamoney.api.repository.LancamentoRepository;
import br.com.wti.algamoney.api.repository.PessoaRepository;
import br.com.wti.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Washington Antunes for wTI on 22/07/2023
 */
@Service
public class LancamentoService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private LancamentoRepository lancamentoRepository;

  public Lancamento salvar(Lancamento lancamento) {
    Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

    if (pessoa.isEmpty() || pessoa.get().isInativo()) {
      throw new PessoaInexistenteOuInativaException();
    }
    return lancamentoRepository.save(lancamento);
  }
}