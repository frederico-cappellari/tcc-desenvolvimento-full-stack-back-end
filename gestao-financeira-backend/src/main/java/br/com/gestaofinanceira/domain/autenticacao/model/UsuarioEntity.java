package br.com.gestaofinanceira.domain.autenticacao.model;

import java.util.List;
import java.util.Set;

import br.com.gestaofinanceira.domain.listacompras.model.ItemCompraEntity;
import br.com.gestaofinanceira.domain.notafiscal.model.NotaFiscalEntity;
import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "USUARIO")
public class UsuarioEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "Usuario_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransacaoFinanceiraEntity> transacoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCompraEntity> listaDeCompras;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotaFiscalEntity> listaNotasFiscais;

    public UsuarioEntity() {}

    public UsuarioEntity(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.transacoes = new java.util.HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<TransacaoFinanceiraEntity> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(Set<TransacaoFinanceiraEntity> transacoes) {
        this.transacoes = transacoes;
    }

    public List<ItemCompraEntity> getListaDeCompras() {
        return listaDeCompras;
    }

    public void setListaDeCompras(List<ItemCompraEntity> listaDeCompras) {
        this.listaDeCompras = listaDeCompras;
    }

    public List<NotaFiscalEntity> getListaNotasFiscais() {
        return listaNotasFiscais;
    }

    public void setListaNotasFiscais(List<NotaFiscalEntity> listaNotasFiscais) {
        this.listaNotasFiscais = listaNotasFiscais;
    }

    @Override
    public String toString() {
        return "UsuarioEntity : {" + "id : " + id +", login : '" + login + '\'' + ", senha : '" + senha + '\'' +'}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        UsuarioEntity other = (UsuarioEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}