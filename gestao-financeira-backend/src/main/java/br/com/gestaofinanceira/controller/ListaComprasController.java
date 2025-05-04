package br.com.gestaofinanceira.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.domain.autenticacao.service.UsuarioAutenticacaoService;
import br.com.gestaofinanceira.domain.listacompras.dto.ItemCompraDTO;
import br.com.gestaofinanceira.domain.listacompras.model.ItemCompraEntity;
import br.com.gestaofinanceira.domain.listacompras.service.ItemCompraService;
import br.com.gestaofinanceira.infra.response.PaginatedResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/lista-compras")
public class ListaComprasController {

    @Inject
    UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Inject
    ItemCompraService itemCompraService;

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consulta extrato financeiro de um mês/ano de um usuário.", description = "Retorna todas as transações do mês para um usuário.")
    @APIResponse(responseCode = "200", description = "Lista de Transações retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemCompraDTO.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response balanco(@PathParam("login") String login) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(login);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }
        Long usuarioId = usuario.getId();
        List<ItemCompraEntity> lista = this.itemCompraService.listarPorUsuarioComValorMedio(usuarioId);
        List<ItemCompraDTO> retorno = lista.stream().map(ItemCompraDTO::new).collect(Collectors.toList());
        return Response.ok().entity(retorno).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Inclui um noto item de compra.", description = "Inclui um Novo Item de Compra.")
    @APIResponse(responseCode = "201", description = "Item de Compra incluído com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemCompraDTO.class)))
    @APIResponse(responseCode = "400", description = "Erro ao incluir a Item de Compra.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response incluiItemDeCompra(ItemCompraDTO itemCompraDTO) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(itemCompraDTO.getUsuarioLogin());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }

        try {
            ItemCompraEntity itemCompraEntity = new ItemCompraEntity();
            itemCompraEntity.setDescricao(itemCompraDTO.getDescricao());
            itemCompraEntity.setUsuario(usuario);
            itemCompraService.salvar(itemCompraEntity);
            return Response.status(Response.Status.CREATED).entity(itemCompraEntity.id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"" + e.getMessage() + "\" }").build();
        }
    }

    @GET
    @Operation(summary = "Lista Itens de Compora do Usuário de Forma Paginada.", description = "Retorna todos Itens de Compra de um usuário de forma paginada.")
    @APIResponse(responseCode = "200", description = "Lista de Notas Fiscais retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemCompraDTO.class)))
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
        List<ItemCompraEntity> lista = itemCompraService.listaPaginada(pagina, tamanhoPagina, asc, usuario.getId(), propriedade);
        List<ItemCompraDTO> retorno = lista.stream().map(ItemCompraDTO::new).collect(Collectors.toList());
        PaginatedResponse<ItemCompraDTO> paginacao = new PaginatedResponse<>(itemCompraService.conta(usuario.getId()), pagina, tamanhoPagina,(int) Math.ceil((double) itemCompraService.conta(usuario.getId()) / tamanhoPagina), asc, retorno);
        return Response.ok().entity(paginacao).build();
    }
}