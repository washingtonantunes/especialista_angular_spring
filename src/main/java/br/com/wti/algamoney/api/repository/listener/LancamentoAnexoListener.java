package br.com.wti.algamoney.api.repository.listener;

import br.com.wti.algamoney.api.EspecialistaAngularSpringApplication;
import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.storage.S3;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;

/**
 * @author Washington Antunes for wTI on 13/08/2023
 */
public class LancamentoAnexoListener {

    @PostLoad
    public void postLoad(Lancamento lancamento) {
        if (StringUtils.hasText(lancamento.getAnexo())) {
            S3 s3 = EspecialistaAngularSpringApplication.getBean(S3.class);
            lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
        }
    }
}
