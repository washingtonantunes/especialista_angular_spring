package br.com.wti.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

/**
 * @author Washington Antunes for wTI on 19/07/2023
 */
public class RecursoCriadoEvent extends ApplicationEvent {

  private HttpServletResponse response;
  private Long codigo;

  public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
    super(source);
    this.response = response;
    this.codigo = codigo;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public Long getCodigo() {
    return codigo;
  }
}
