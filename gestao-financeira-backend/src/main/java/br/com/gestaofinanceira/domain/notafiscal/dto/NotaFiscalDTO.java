package br.com.gestaofinanceira.domain.notafiscal.dto;

import java.time.format.DateTimeFormatter;

import br.com.gestaofinanceira.domain.notafiscal.model.NotaFiscalEntity;

public class NotaFiscalDTO {

    private String chaveDeAcesso;
    private String data;
    private String valorTotal;
    private Boolean possuiItensNaoEncontradosNaLista;
    private String situacao;
    private String usuarioLogin;

    public NotaFiscalDTO() {
    }

    public NotaFiscalDTO(NotaFiscalEntity entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.chaveDeAcesso = entity.getChaveDeAcesso();
        this.data = entity.getData() != null ? entity.getData().format(formatter) : "";
        this.valorTotal = entity.getValorTotal() != null ? entity.getValorTotal().toString() : null;
        this.possuiItensNaoEncontradosNaLista = entity.getPossuiItensNaoEncontradosNaLista();
        this.situacao = entity.getSituacao() != null ? entity.getSituacao().getSituacao() : null;
        this.usuarioLogin = entity.getUsuario() != null ? entity.getUsuario().getLogin() : null;
    }

    public String getChaveDeAcesso() {
        return chaveDeAcesso;
    }

    public void setChaveDeAcesso(String chaveDeAcesso) {
        this.chaveDeAcesso = chaveDeAcesso;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getPossuiItensNaoEncontradosNaLista() {
        return possuiItensNaoEncontradosNaLista;
    }

    public void setPossuiItensNaoEncontradosNaLista(Boolean possuiItensNaoEncontradosNaLista) {
        this.possuiItensNaoEncontradosNaLista = possuiItensNaoEncontradosNaLista;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin; 
    }
}
