package br.com.gestaofinanceira.domain.transacaofinanceira.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.domain.categoria.model.CategoriaEntity;
import br.com.gestaofinanceira.infra.enums.TipoTransacaoFinanceiraEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "TRANSACAO_FINANCEIRA")
public class TransacaoFinanceiraEntity extends PanacheEntity {

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacaoFinanceiraEnum tipo;

    @Column(nullable = false)
    private boolean recorrente;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoriaEntity categoria;

    @Transient
    private BigDecimal valorMedio;


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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(BigDecimal valorMedio) {
        this.valorMedio = valorMedio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((super.id == null) ? 0 : super.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransacaoFinanceiraEntity other = (TransacaoFinanceiraEntity) obj;
        if (super.id == null) {
            if (other.id != null)
                return false;
        } else if (!super.id.equals(other.id))
            return false;
        return true;
    }
    
}