package br.com.gestaofinanceira.domain.autenticacao.repository;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {
    public UsuarioEntity findByLogin(String login) {
        return find("login", login).firstResult();
    }
}
