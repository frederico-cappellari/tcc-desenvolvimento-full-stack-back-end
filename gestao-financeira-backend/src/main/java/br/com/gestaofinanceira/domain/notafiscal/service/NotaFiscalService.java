package br.com.gestaofinanceira.domain.notafiscal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.gestaofinanceira.domain.notafiscal.model.NotaFiscalEntity;
import br.com.gestaofinanceira.domain.notafiscal.repository.NotaFiscalRepository;
import br.com.gestaofinanceira.infra.enums.SituacaoNotaFiscalEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class NotaFiscalService {

    @Inject
    NotaFiscalRepository repository;

    @Inject
    NotaFiscalMessageService notaFiscalMessageService;

    @Transactional
    public NotaFiscalEntity salvar(NotaFiscalEntity notaFiscal) {
        if (notaFiscal == null) {
            throw new IllegalArgumentException("A Nota Fiscal deve ser informada.");
        }
        if (notaFiscal.getChaveDeAcesso() == null || notaFiscal.getChaveDeAcesso().isEmpty()) {
            throw new IllegalArgumentException("A Chave de Acesso deve ser informada.");
        }
        notaFiscal.setData(LocalDate.now());
        notaFiscal.setSituacao(SituacaoNotaFiscalEnum.NAO_PROCESSADA);
        repository.persistAndFlush(notaFiscal);
        notaFiscalMessageService.sendMessage(notaFiscal.getChaveDeAcesso());
        return notaFiscal;
    }

    public List<NotaFiscalEntity> listaPaginada(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        long totalEntidades = repository.count();
        if (totalEntidades == 0) {
            return new ArrayList<NotaFiscalEntity>();
        }
        int qntPagina = (int) Math.ceil((double) totalEntidades / tamanhoPagina);
        if (pagina > qntPagina || pagina < 1) {
            throw new IllegalArgumentException("Página fora do intervalo.");
        }
        return repository.listaPaginadaComOrdenacao(pagina, tamanhoPagina, asc, usuarioId, propriedade);
    }

    public long conta(Long usuarioId) {
        return repository.conta(usuarioId);
    }

}
