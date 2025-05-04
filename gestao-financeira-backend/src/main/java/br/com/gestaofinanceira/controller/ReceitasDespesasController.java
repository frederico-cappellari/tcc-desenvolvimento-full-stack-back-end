package br.com.gestaofinanceira.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.gestaofinanceira.domain.autenticacao.model.UsuarioEntity;
import br.com.gestaofinanceira.domain.autenticacao.service.UsuarioAutenticacaoService;
import br.com.gestaofinanceira.domain.categoria.model.CategoriaEntity;
import br.com.gestaofinanceira.domain.categoria.service.CategoriaService;
import br.com.gestaofinanceira.domain.transacaofinanceira.dto.GastoMensalDTO;
import br.com.gestaofinanceira.domain.transacaofinanceira.dto.ReceitasDespesasMesDTORequest;
import br.com.gestaofinanceira.domain.transacaofinanceira.dto.TransacaoFinanceiraDTO;
import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import br.com.gestaofinanceira.domain.transacaofinanceira.service.TransacaoFinanceiraService;
import br.com.gestaofinanceira.infra.response.PaginatedResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/receitas-despesas")
public class ReceitasDespesasController {

    @Inject
    UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Inject
    TransacaoFinanceiraService transacaoFinanceiraService;

    @Inject
    CategoriaService categoriaService;

    @GET
    @Path("/extrato-mensal")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consulta extrato financeiro de um mês/ano de um usuário.", description = "Retorna todas as transações do mês para um usuário.")
    @APIResponse(responseCode = "200", description = "Lista de Transações retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TransacaoFinanceiraDTO.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response extratoPeriodo(@BeanParam ReceitasDespesasMesDTORequest receitasDespesasMesDTORequest) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(receitasDespesasMesDTORequest.getLogin());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }
        Long usuarioId = usuario.getId();
        List<TransacaoFinanceiraEntity> lista = this.transacaoFinanceiraService.listarMesAnoUsuario(usuarioId,receitasDespesasMesDTORequest.getMesAno());
        List<TransacaoFinanceiraDTO> retorno = lista.stream().map(TransacaoFinanceiraDTO::new).collect(Collectors.toList());
        System.out.println(lista.size());
        return Response.ok().entity(retorno).build();
    }

    @GET
    @Path("/historico-semestre/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consulta extrato financeiro de um mês/ano de um usuário.", description = "Retorna todas as transações do mês para um usuário.")
    @APIResponse(responseCode = "200", description = "Lista de Transações retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TransacaoFinanceiraDTO.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response historicoSemestre(@PathParam("login") String login) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(login);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }

        List<GastoMensalDTO> retorno = new ArrayList<GastoMensalDTO>();
        // Para os ultimos 6 meses buscar o total de despesas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth anoMesAtual = YearMonth.now();
        for (int i = 1; i < 7; i++) {
            YearMonth anoMes = anoMesAtual.minusMonths(i);
            String mesAno = anoMes.format(formatter);
            retorno.add(new GastoMensalDTO(mesAno,this.transacaoFinanceiraService.buscaTotalGastosMes(usuario.getId(), mesAno)));
        }
        return Response.ok().entity(retorno).build();
    }

    // O end point abaixo é responsável por incluir uma nova transação financeira
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Inclui uma nova transação financeira.", description = "Inclui uma nova transação financeira.")
    @APIResponse(responseCode = "201", description = "Transação financeira incluída com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TransacaoFinanceiraDTO.class)))
    @APIResponse(responseCode = "400", description = "Erro ao incluir a transação financeira.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @APIResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class)))
    @Authenticated
    public Response incluiTransacaoFinanceira(TransacaoFinanceiraDTO transacaoFinanceiraDTO) {
        UsuarioEntity usuario = usuarioAutenticacaoService.buscarPorLogin(transacaoFinanceiraDTO.getUsuarioLogin());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Usuário não encontrado.\"}").build();
        }

        CategoriaEntity categoria = categoriaService.buscarPorId(transacaoFinanceiraDTO.getCategoriaId());
        if (categoria == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"erro\":\"Categoria não foi encontrada.\"}").build();
        }

        try {
            TransacaoFinanceiraEntity transacao = new TransacaoFinanceiraEntity();
            transacao.setUsuario(usuario);
            transacao.setCategoria(categoria);
            transacao.setDescricao(transacaoFinanceiraDTO.getDescricao());
            transacao.setValor(transacaoFinanceiraDTO.getValor());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            transacao.setData(LocalDate.parse(transacaoFinanceiraDTO.getData(), formatter));

            transacao.setRecorrente(transacaoFinanceiraDTO.isRecorrente());
            transacao.setTipo(transacaoFinanceiraDTO.getTipo());
            TransacaoFinanceiraEntity transacaoSalva = transacaoFinanceiraService.salvar(transacao);
            return Response.status(Response.Status.CREATED).entity(transacaoSalva.id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"" + e.getMessage() + "\" }").build();
        }
    }

    @GET
    @Operation(summary = "Lista Transações Financeiras do Usuário.", description = "Retorna todas as transações de um usuário de forma paginada.")
    @APIResponse(responseCode = "200", description = "Lista de Transações retornadas com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TransacaoFinanceiraDTO.class)))
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
        List<TransacaoFinanceiraEntity> lista = transacaoFinanceiraService.listaPaginada(pagina, tamanhoPagina, asc, usuario.getId(), propriedade);
        List<TransacaoFinanceiraDTO> retorno = lista.stream().map(TransacaoFinanceiraDTO::new).collect(Collectors.toList());
        PaginatedResponse<TransacaoFinanceiraDTO> paginacao = new PaginatedResponse<>(transacaoFinanceiraService.conta(usuario.getId()), pagina, tamanhoPagina,
                (int) Math.ceil((double) transacaoFinanceiraService.conta(usuario.getId()) / tamanhoPagina), asc, retorno);
        return Response.ok().entity(paginacao).build();
    }
}