package br.com.wti.algamoney.api.repository;

import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Washington Antunes for wTI on 19/07/2023
 */
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
