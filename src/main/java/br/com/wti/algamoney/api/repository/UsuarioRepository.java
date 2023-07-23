package br.com.wti.algamoney.api.repository;

import br.com.wti.algamoney.api.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  public Optional<Usuario> findByEmail(String email);
}
