package br.com.wti.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
@ConfigurationProperties("algamoney")
@Component
public class AlgamoneyApiProperty {

  private String originPermitida = "http://localhost:8000";

  private final Seguranca seguranca = new Seguranca();

  public String getOriginPermitida() {
    return originPermitida;
  }

  public void setOriginPermitida(String originPermitida) {
    this.originPermitida = originPermitida;
  }

  public Seguranca getSeguranca() {
    return seguranca;
  }

  public static class Seguranca {

    private boolean enableHttps;

    public boolean isEnableHttps() {
      return enableHttps;
    }

    public void setEnableHttps(boolean enableHttps) {
      this.enableHttps = enableHttps;
    }
  }
}
