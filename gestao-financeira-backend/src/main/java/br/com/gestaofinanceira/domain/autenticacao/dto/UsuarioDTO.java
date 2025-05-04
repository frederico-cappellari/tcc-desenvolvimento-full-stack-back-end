package br.com.gestaofinanceira.domain.autenticacao.dto;

public class UsuarioDTO {

    private String login;
    private String senha;

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

    @Override
    public String toString() {
        return "UsuarioDTO {" +" login : '" + login + '\'' +", senha : '" + senha + '\'' + " }";
    }
}
