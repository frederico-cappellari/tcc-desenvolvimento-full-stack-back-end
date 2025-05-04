package br.com.gestaofinanceira.controller;

import br.com.gestaofinanceira.domain.autenticacao.dto.UsuarioDTO;
import br.com.gestaofinanceira.domain.autenticacao.service.UsuarioAutenticacaoService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@PermitAll
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioAutenticacaoController {

    @Inject
    UsuarioAutenticacaoService usuarioAutenticacaoService;

    @POST
    public Response login(UsuarioDTO dto) {
        try {
            String token = usuarioAutenticacaoService.autenticar(dto);
            return Response.ok().entity("{\"token\":\"" + token + "\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }
}