package br.com.wti.algamoney.api.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  private Long codigo;

  private String nome;
  private String email;
  private String senha;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
  private List<Permissao> permissoes;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public List<Permissao> getPermissoes() {
    return permissoes;
  }

  public void setPermissoes(List<Permissao> permissoes) {
    this.permissoes = permissoes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Usuario usuario = (Usuario) o;

    return codigo.equals(usuario.codigo);
  }

  @Override
  public int hashCode() {
    return codigo.hashCode();
  }
}
