package br.com.gestaofinanceira.domain.categoria.dto;

import br.com.gestaofinanceira.domain.categoria.model.CategoriaEntity;

public class CategoriaDTO {
    
    private Long id;

    private String descricao;

    public CategoriaDTO() {
    }

    public CategoriaDTO(CategoriaEntity categoria) {
        this.id = categoria.id;
        this.descricao = categoria.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CategoriaDTO {" + "id : " + id + ", descricao: '" + descricao + '\'' +'}';
    }
    
}
