package br.com.gestaofinanceira.domain.listacompras.model;

import java.math.BigDecimal;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ITEM_COMPRA")
public class ItemCompraEntity extends PanacheEntity {

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioEntity usuario;

    @Transient
    private BigDecimal valorMedio;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
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
        ItemCompraEntity other = (ItemCompraEntity) obj;
        if (super.id == null) {
            if (other.id != null)
                return false;
        } else if (!super.id.equals(other.id))
            return false;
        return true;
    }
    
    
}