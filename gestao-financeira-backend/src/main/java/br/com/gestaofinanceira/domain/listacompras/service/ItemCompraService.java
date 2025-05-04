package br.com.gestaofinanceira.domain.listacompras.service;

import java.util.ArrayList;
import java.util.List;

import br.com.gestaofinanceira.domain.listacompras.model.ItemCompraEntity;
import br.com.gestaofinanceira.domain.listacompras.repositoy.ItemCompraRepository;
import br.com.gestaofinanceira.domain.transacaofinanceira.service.TransacaoFinanceiraService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ItemCompraService {

    @Inject
    ItemCompraRepository repository;

    @Inject
    TransacaoFinanceiraService transacaoFinanceiraService;


    public List<ItemCompraEntity> listarTodos() {
        return repository.listAll();
    }

    public List<ItemCompraEntity> listarPorUsuarioComValorMedio(Long usuarioId) {
        List<ItemCompraEntity> itens = repository.listarPorUsuario(usuarioId);
        itens.stream().forEach(item -> { item.setValorMedio(transacaoFinanceiraService.buscaValorMedio(item.getDescricao()));});
        return itens;
    }	

    @Transactional
    public void salvar(ItemCompraEntity itemCompraEntity) {
        if (itemCompraEntity == null) {
            throw new IllegalArgumentException("A Item de Compra deve ser informado.");
        }
        if (itemCompraEntity.getDescricao() == null || itemCompraEntity.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A Descrição deve ser informada.");
        }
        repository.persist(itemCompraEntity);
    }

    public List<ItemCompraEntity> listaPaginada(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        long totalEntidades = repository.count();
        if (totalEntidades == 0) {
            return new ArrayList<ItemCompraEntity>();
        }
        int qntPagina = (int) Math.ceil((double) totalEntidades / tamanhoPagina);
        if (pagina > qntPagina || pagina < 1) {
            throw new IllegalArgumentException("Página fora do intervalo.");
        }
        List<ItemCompraEntity> itens = repository.listaPaginadaComOrdenacao(pagina, tamanhoPagina, asc, usuarioId, propriedade);
        itens.stream().forEach(item -> { item.setValorMedio(transacaoFinanceiraService.buscaValorMedio(item.getDescricao()));});
        return itens;
    }

    public long conta(Long usuarioId) {
        return repository.conta(usuarioId);
    }

}