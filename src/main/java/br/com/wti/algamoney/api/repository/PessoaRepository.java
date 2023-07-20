package br.com.wti.algamoney.api.repository;

import br.com.wti.algamoney.api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Washington Antunes for wTI on 19/07/2023
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
