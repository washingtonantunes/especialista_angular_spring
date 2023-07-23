package br.com.wti.algamoney.api.repository.lancamento;

import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.repository.filter.LancamentoFilter;
import br.com.wti.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Washington Antunes for wTI on 22/07/2023
 */
public interface LancamentoRepositoryQuery {

  public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

  public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
