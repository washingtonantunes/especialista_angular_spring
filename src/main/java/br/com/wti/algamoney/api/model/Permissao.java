package br.com.wti.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
@Entity
@Table(name = "permissao")
public class Permissao {

  @Id
  private Long codigo;
  private String descricao;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Permissao permissao = (Permissao) o;

    return codigo.equals(permissao.codigo);
  }

  @Override
  public int hashCode() {
    return codigo.hashCode();
  }
}
