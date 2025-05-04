package br.com.gestaofinanceira.domain.listacompras.dto;

import java.math.BigDecimal;

import br.com.gestaofinanceira.domain.listacompras.model.ItemCompraEntity;

public class ItemCompraDTO {
    
    private String descricao;

    private BigDecimal valorMedio;

    private String usuarioLogin;

    public ItemCompraDTO() {}

    public ItemCompraDTO(ItemCompraEntity itemCompra) {
        this.descricao = itemCompra.getDescricao();
        this.valorMedio = itemCompra.getValorMedio();
        this.usuarioLogin = itemCompra.getUsuario().getLogin();
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    
}
