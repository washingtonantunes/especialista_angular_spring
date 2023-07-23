package br.com.wti.algamoney.api.token;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;
import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Washington Antunes for wTI on 22/07/2023
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

   @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    final String OAUTH_TOKEN = "/oauth/token";
    final String REFRESH_TOKEN = "refresh_token";

    if (OAUTH_TOKEN.equalsIgnoreCase(req.getRequestURI())
        && REFRESH_TOKEN.equals(req.getParameter("grant_type"))
        && req.getCookies() != null) {

      String refreshToken = Stream.of(req.getCookies())
          .filter(cookie -> "refreshToken".equals(cookie.getName()))
          .findFirst()
          .map(Cookie::getValue)
          .orElse(null);

      req = new MyServletRequestWrapper(req, refreshToken);
    }

    chain.doFilter(req, response);
  }

  static class MyServletRequestWrapper extends HttpServletRequestWrapper {

    private final String refreshToken;

    public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
      super(request);
      this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
      ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
      map.put("refresh_token", new String[] { refreshToken });
      map.setLocked(true);
      return map;
    }
  }
}
