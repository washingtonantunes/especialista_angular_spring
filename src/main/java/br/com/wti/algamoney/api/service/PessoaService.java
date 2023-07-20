package br.com.wti.algamoney.api.service;

import br.com.wti.algamoney.api.model.Pessoa;
import br.com.wti.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Washington Antunes for wTI on 20/07/2023
 */
@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  public Pessoa atualizar(Long codigo, Pessoa pessoa) {
    Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
    BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
    return pessoaRepository.save(pessoaSalva);
  }

  public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
    Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
    pessoaSalva.setAtivo(ativo);
    pessoaRepository.save(pessoaSalva);
  }

  private Pessoa buscarPessoaPeloCodigo(Long codigo) {
    return pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
  }
}
