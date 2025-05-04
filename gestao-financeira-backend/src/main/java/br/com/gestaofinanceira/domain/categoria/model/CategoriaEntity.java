package br.com.gestaofinanceira.domain.categoria.model;

import java.util.List;

import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CATEGORIA")
public class CategoriaEntity extends PanacheEntity {

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransacaoFinanceiraEntity> listaTransacaoFinanceiras;


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<TransacaoFinanceiraEntity> getListaTransacaoFinanceiras() {
        return listaTransacaoFinanceiras;
    }

    public void setListaTransacaoFinanceiras(List<TransacaoFinanceiraEntity> listaTransacaoFinanceiras) {
        this.listaTransacaoFinanceiras = listaTransacaoFinanceiras;
    }

    @Override
    public String toString() {
        return "CategoriaEntity [id=" + super.id + ", descricao=" + descricao;
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
        CategoriaEntity other = (CategoriaEntity) obj;
        if (super.id == null) {
            if (other.id != null)
                return false;
        } else if (!super.id.equals(other.id))
            return false;
        return true;
    }
    
    
}