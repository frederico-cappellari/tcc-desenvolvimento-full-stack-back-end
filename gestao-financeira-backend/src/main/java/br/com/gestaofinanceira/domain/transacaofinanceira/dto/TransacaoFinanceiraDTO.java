package br.com.gestaofinanceira.domain.transacaofinanceira.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import br.com.gestaofinanceira.infra.enums.TipoTransacaoFinanceiraEnum;

public class TransacaoFinanceiraDTO {

    private String descricao;

    private TipoTransacaoFinanceiraEnum tipo;

    private String categoriaDescricao;

    private Long categoriaId;

    private boolean recorrente;

    private BigDecimal valor;

    private String data;

    private BigDecimal valorMedio;

    private String usuarioLogin;

    public TransacaoFinanceiraDTO() {
    }

    public TransacaoFinanceiraDTO(TransacaoFinanceiraEntity entity) {
        // tranforma data LocalDate em String no formato dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = entity.getData() != null ? entity.getData().format(formatter) : "";
        this.descricao = entity.getDescricao();
        this.tipo = entity.getTipo();
        this.recorrente = entity.isRecorrente();
        this.valor = entity.getValor();
        this.valorMedio = entity.getValorMedio();
        this.categoriaId = entity.getCategoria() != null ? entity.getCategoria().id : null;
        this.categoriaDescricao = entity.getCategoria() != null ? entity.getCategoria().getDescricao() : null;
        this.usuarioLogin = entity.getUsuario() != null ? entity.getUsuario().getLogin() : null;
    }

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoTransacaoFinanceiraEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacaoFinanceiraEnum tipo) {
        this.tipo = tipo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaDescricao() {
        return categoriaDescricao;
    }

    public void setCategoriaDescricao(String categoriaDescricao) {
        this.categoriaDescricao = categoriaDescricao;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public void setRecorrente(boolean recorrente) {
        this.recorrente = recorrente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(BigDecimal valorMedio) {
        this.valorMedio = valorMedio;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    @Override
    public String toString() {
        return "TransacaoFinanceiraDTO {" +
                "descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                ", categoriaId=" + categoriaId +
                ", categoriaDescricao='" + categoriaDescricao + '\'' +
                ", recorrente=" + recorrente +
                ", valor=" + valor +
                ", data='" + data + '\'' +
                ", valorMedio=" + valorMedio +
                ", usuarioLogin='" + usuarioLogin + '\'' +
                '}';
    }
}
