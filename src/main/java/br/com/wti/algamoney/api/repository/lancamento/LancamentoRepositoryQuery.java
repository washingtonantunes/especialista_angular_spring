package br.com.wti.algamoney.api.repository.lancamento;

import br.com.wti.algamoney.api.dto.LancamentoEstatisticaCategoria;
import br.com.wti.algamoney.api.dto.LancamentoEstatisticaDia;
import br.com.wti.algamoney.api.dto.LancamentoEstatisticaPessoa;
import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.repository.filter.LancamentoFilter;
import br.com.wti.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Washington Antunes for wTI on 22/07/2023
 */
public interface LancamentoRepositoryQuery {

    public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
    public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
    public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
