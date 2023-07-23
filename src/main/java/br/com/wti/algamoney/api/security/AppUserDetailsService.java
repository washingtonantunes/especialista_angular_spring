package br.com.wti.algamoney.api.security;

import br.com.wti.algamoney.api.model.Usuario;
import br.com.wti.algamoney.api.repository.UsuarioRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
    Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
    return new User(email, usuario.getSenha(), getPermissoes(usuario));
  }

  private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
    return authorities;
  }
}
