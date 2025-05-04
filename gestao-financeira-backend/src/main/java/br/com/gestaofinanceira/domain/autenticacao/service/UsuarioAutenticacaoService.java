package br.com.gestaofinanceira.domain.autenticacao.service;

import java.util.Objects;

import br.com.gestaofinanceira.domain.autenticacao.dto.UsuarioDTO;
import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.domain.autenticacao.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioAutenticacaoService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JwtService jwtService;

    public String autenticar(UsuarioDTO dto) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(dto.getLogin());
        if (usuario == null || !Objects.equals(usuario.getSenha(), dto.getSenha())) {
            throw new RuntimeException("Login ou senha inválidos");
        }
        return jwtService.generateToken(usuario.getLogin());
    }

    public UsuarioEntity buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}