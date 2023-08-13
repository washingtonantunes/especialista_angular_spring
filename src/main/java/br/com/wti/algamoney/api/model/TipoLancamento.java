package br.com.wti.algamoney.api.model;

/**
 * @author Washington Antunes for wTI on 20/07/2023
 */
public enum TipoLancamento {

  RECEITA("Receita"),
  DESPESA("Despesa");

  private final String descricao;

  TipoLancamento(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
