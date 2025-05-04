package br.com.gestaofinanceira.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.gestaofinanceira.domain.categoria.dto.CategoriaDTO;
import br.com.gestaofinanceira.domain.categoria.service.CategoriaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("receitas-despesas/categoria")
public class ListaCategoriaController {

    @Inject
    CategoriaService categoriaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lista Categorias.", description = "Retorna todas as categorias.")
    @APIResponse(responseCode = "200", description = "Lista de Categorias retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CategoriaDTO.class)))
    @Authenticated
    public Response listaTodasCategorias() {
        List<CategoriaDTO> retorno = this.categoriaService.listarTodas().stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return Response.ok().entity(retorno).build();
    }
}