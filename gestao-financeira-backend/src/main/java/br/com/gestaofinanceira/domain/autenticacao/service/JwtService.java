package br.com.gestaofinanceira.domain.autenticacao.service;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateToken(String login) {
        return Jwt.issuer(issuer).upn(login).groups(new HashSet<>(Arrays.asList("user"))).sign();
    }

}