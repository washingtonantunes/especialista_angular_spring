package br.com.wti.algamoney.api.security;

import br.com.wti.algamoney.api.model.Usuario;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
public class UsuarioSistema extends User {

  private Usuario usuario;

  public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
    super(usuario.getEmail(), usuario.getSenha(), authorities);
    this.usuario = usuario;
  }

  public Usuario getUsuario() {
    return usuario;
  }
}
