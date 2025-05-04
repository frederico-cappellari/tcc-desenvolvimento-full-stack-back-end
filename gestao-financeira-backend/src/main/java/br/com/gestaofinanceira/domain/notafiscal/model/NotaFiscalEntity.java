package br.com.gestaofinanceira.domain.notafiscal.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.infra.enums.SituacaoNotaFiscalEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "NOTA_FISCAL")
public class NotaFiscalEntity extends PanacheEntity {

    @Column(nullable = false)
    private String chaveDeAcesso;

    @Column(nullable = false)
    private LocalDate data;

    private BigDecimal valorTotal;

    private Boolean possuiItensNaoEncontradosNaLista;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoNotaFiscalEnum situacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioEntity usuario;

    public String getChaveDeAcesso() {
        return chaveDeAcesso;
    }

    public void setChaveDeAcesso(String chaveDeAcesso) {
        this.chaveDeAcesso = chaveDeAcesso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getPossuiItensNaoEncontradosNaLista() {
        return possuiItensNaoEncontradosNaLista;
    }

    public void setPossuiItensNaoEncontradosNaLista(Boolean possuiItensNaoEncontradosNaLista) {
        this.possuiItensNaoEncontradosNaLista = possuiItensNaoEncontradosNaLista;
    }

    public SituacaoNotaFiscalEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoNotaFiscalEnum situacao) {
        this.situacao = situacao;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

}
