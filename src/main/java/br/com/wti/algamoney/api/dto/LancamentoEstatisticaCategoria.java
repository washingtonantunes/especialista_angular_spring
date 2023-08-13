package br.com.wti.algamoney.api.dto;

import br.com.wti.algamoney.api.model.Categoria;

import java.math.BigDecimal;

/**
 * @author Washington Antunes for wTI on 09/08/2023
 */
public class LancamentoEstatisticaCategoria {

    private Categoria categoria;

    private BigDecimal total;

    public LancamentoEstatisticaCategoria(Categoria categoria, BigDecimal total) {
        this.categoria = categoria;
        this.total = total;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
