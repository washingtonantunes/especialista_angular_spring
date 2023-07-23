package br.com.wti.algamoney.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Washington Antunes for wTI on 23/07/2023
 */
public class GeradorSenha {

  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.encode("m0b1l30"));
  }
}
