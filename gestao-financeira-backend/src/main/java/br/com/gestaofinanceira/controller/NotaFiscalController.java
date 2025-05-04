package br.com.gestaofinanceira.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.domain.autenticacao.service.UsuarioAutenticacaoService;
import br.com.gestaofinanceira.domain.notafiscal.dto.NotaFiscalDTO;
import br.com.gestaofinanceira.domain.notafiscal.model.NotaFiscalEntity;
import br.com.gestaofinanceira.domain.notafiscal.service.NotaFiscalService;
import br.com.gestaofinanceira.infra.response.PaginatedResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/nota-fiscal")
public class NotaFiscalController {

    @Inject
    UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Inject
    NotaFiscalService notaFiscalService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Inclui uma nova nota fiscal.", description = "Inclui uma nova nota fiscal.")
    @APIResponse(responseCode = "201", description = "Nota Fiscal incluída com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NotaFiscalDTO.class)))
    @APIResponse(responseCode = "400", description = "Erro ao incluir a Nota Fiscal.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response incluiNotaFiscal(NotaFiscalDTO notaFiscalDTO) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(notaFiscalDTO.getUsuarioLogin());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }

        try {
            NotaFiscalEntity notaFiscal = new NotaFiscalEntity();
            notaFiscal.setChaveDeAcesso(notaFiscalDTO.getChaveDeAcesso());
            notaFiscal.setUsuario(usuario);
            notaFiscalService.salvar(notaFiscal);
            return Response.status(Response.Status.CREATED).entity(notaFiscal.id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"" + e.getMessage() + "\" }").build();
        }
    }

    @GET
    @Operation(summary = "Lista Notas Fiscais do Usuário.", description = "Retorna todas as Notas Fiscais de um usuário de forma paginada.")
    @APIResponse(responseCode = "200", description = "Lista de Notas Fiscais retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NotaFiscalDTO.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response lista(@QueryParam("pagina") @DefaultValue("1") int pagina,
            @QueryParam("tamanhoPagina") @DefaultValue("10") int tamanhoPagina,
            @QueryParam("asc") @DefaultValue("true") boolean asc,
            @QueryParam("propriedade") String propriedade,
            @QueryParam("login") String login) {

        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(login);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }
        List<NotaFiscalEntity> lista = notaFiscalService.listaPaginada(pagina, tamanhoPagina, asc, usuario.getId(), propriedade);
        List<NotaFiscalDTO> retorno = lista.stream().map(NotaFiscalDTO::new).collect(Collectors.toList());
        PaginatedResponse<NotaFiscalDTO> paginacao = new PaginatedResponse<>(notaFiscalService.conta(usuario.getId()), pagina, tamanhoPagina,(int) Math.ceil((double) notaFiscalService.conta(usuario.getId()) / tamanhoPagina), asc, retorno);
        return Response.ok().entity(paginacao).build();
    }
}
