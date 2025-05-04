package br.com.gestaofinanceira.domain.transacaofinanceira.dto;

import jakarta.ws.rs.QueryParam;

public class ReceitasDespesasMesDTORequest {
    
    @QueryParam("login")
    private String login;
    
    @QueryParam("mesAno")
    private String mesAno;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }
}
