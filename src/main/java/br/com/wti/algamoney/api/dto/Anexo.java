package br.com.wti.algamoney.api.dto;

/**
 * @author Washington Antunes for wTI on 12/08/2023
 */
public class Anexo {

    private String nome;

    private String url;

    public Anexo(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
