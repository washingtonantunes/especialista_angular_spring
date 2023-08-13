package br.com.wti.algamoney.api.service;

import br.com.wti.algamoney.api.dto.LancamentoEstatisticaPessoa;
import br.com.wti.algamoney.api.mail.Mailer;
import br.com.wti.algamoney.api.model.Lancamento;
import br.com.wti.algamoney.api.model.Pessoa;
import br.com.wti.algamoney.api.model.Usuario;
import br.com.wti.algamoney.api.repository.LancamentoRepository;
import br.com.wti.algamoney.api.repository.PessoaRepository;
import br.com.wti.algamoney.api.repository.UsuarioRepository;
import br.com.wti.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import br.com.wti.algamoney.api.storage.S3;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Washington Antunes for wTI on 22/07/2023
 */
@Service
public class LancamentoService {

    private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";

    private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Mailer mailer;

    @Autowired
    private S3 s3;

    public Lancamento salvar(Lancamento lancamento) {
        validarPessoa(lancamento);

        if(StringUtils.hasText(lancamento.getAnexo())) {
            s3.salvar(lancamento.getAnexo());
        }
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
            validarPessoa(lancamento);
        }

        if(!StringUtils.hasText(lancamento.getAnexo())
                && StringUtils.hasText(lancamentoSalvo.getAnexo())) {
            s3.remover(lancamentoSalvo.getAnexo());
        } else if(StringUtils.hasText(lancamento.getAnexo())
                && !lancamento.getAnexo().equals(lancamentoSalvo.getAnexo())) {
            s3.substituir(lancamentoSalvo.getAnexo(), lancamento.getAnexo());
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Optional<Pessoa> pessoa = Optional.empty();
        if (lancamento.getPessoa().getCodigo() != null) {
            pessoa = buscarPessoa(lancamento.getPessoa().getCodigo());
        }

        if (pessoa.isEmpty() || pessoa.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
        return lancamentoRepository.findById(codigo).orElseThrow(IllegalArgumentException::new);
    }

    private Optional<Pessoa> buscarPessoa(Long codigo) {
        return pessoaRepository.findById(codigo);
    }

    public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
        List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("DT_INICIO", Date.valueOf(inicio));
        parametros.put("DT_FIM", Date.valueOf(inicio));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Scheduled(cron = "0 0 6 * * *", zone = "America/Sao_Paulo")
    public void avisarSobreLancamentosVencidos() {
        if (logger.isDebugEnabled()) {
            logger.debug("Preparando envio de "
                    + "e-mails de aviso de lançamentos vencidos.");
        }

        List<Lancamento> vencidos = lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());

        if (vencidos.isEmpty()) {
            logger.info("Sem lançamentos vencidos para aviso.");
            return;
        }

        logger.info("Existem {} lançamentos vencidos.", vencidos.size());

        List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);

        if (destinatarios.isEmpty()) {
            logger.warn("Existem lançamentos vencidos, mas o "
                    + "sistema não encontrou destinatários.");
            return;
        }

        mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);

        logger.info("Envio de e-mail de aviso concluído.");
    }
}
