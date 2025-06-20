package br.com.gestaofinanceira.domain.transacaofinanceira.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import br.com.gestaofinanceira.domain.transacaofinanceira.repositoy.TransacaoFinanceiraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransacaoFinanceiraService {

    @Inject
    TransacaoFinanceiraRepository repository;

    private static final Integer QUANTIDADE_DE_REGISTROS = 10;

    public List<TransacaoFinanceiraEntity> listarTodas() {
        return repository.listAll();
    }

    public List<TransacaoFinanceiraEntity> listarMesAnoUsuario(Long usuarioId, String MesAno) {
        if (MesAno == null || MesAno.isEmpty()) {
            throw new IllegalArgumentException("O parâmetro Mes/Ano não pode ser nulo ou vazio.");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("O parâmetro Usuário não pode ser nulo.");
        }
        List<TransacaoFinanceiraEntity> transacoes = repository.buscarPorMesAnoUsuario(usuarioId, MesAno);
        transacoes.stream().forEach(transacao -> { transacao.setValorMedio(buscaValorMedio(transacao.getDescricao()));});
        return transacoes;
    }

    public BigDecimal buscaValorMedio(String descricao) {
        BigDecimal valorMedio = BigDecimal.ZERO;
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("O parâmetro Descrição não pode ser nulo ou vazio.");
        }
        List<TransacaoFinanceiraEntity> transacoes = repository.buscarUltimasTransacoes(descricao, QUANTIDADE_DE_REGISTROS);
        for (TransacaoFinanceiraEntity transacaoFinanceira : transacoes) {
            valorMedio = valorMedio.add(transacaoFinanceira.getValor());
        }
        
        if (transacoes.size() == 0) {
            return valorMedio;
        }
        valorMedio = valorMedio.divide(BigDecimal.valueOf(transacoes.size()), RoundingMode.HALF_EVEN);
        return valorMedio;

    }

    @Transactional
    public TransacaoFinanceiraEntity salvar(TransacaoFinanceiraEntity transacao) {
        if (transacao == null) {
            throw new IllegalArgumentException("A Transação deve ser informada.");
        }
        if (transacao.getDescricao() == null || transacao.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A Descrição deve ser informada.");
        }
        if (transacao.getValor() == null) {
            throw new IllegalArgumentException("O Valor deve ser informado.");
        }
        if (transacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O Valor não pode ser negativo.");
        }
        if (transacao.getTipo() == null) {
            throw new IllegalArgumentException("O Tipo deve ser informado.");
        }
        if (transacao.getData() == null) {
            throw new IllegalArgumentException("A Data deve ser informada.");
        }
        if (transacao.getCategoria() == null) {
            throw new IllegalArgumentException("A Categoria deve ser informada.");
        }
        repository.persistAndFlush(transacao);
        return transacao;
    }

    public TransacaoFinanceiraEntity buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public BigDecimal buscaTotalGastosMes(Long idUsuario, String mesAno) {
        if (mesAno == null || mesAno.isEmpty()) {
            throw new IllegalArgumentException("O parâmetro Mes/Ano não pode ser nulo ou vazio.");
        }
        if (idUsuario == null) {
            throw new IllegalArgumentException("O parâmetro Usuário não pode ser nulo.");
        }
        List<TransacaoFinanceiraEntity> despesas = repository.buscarTotalGastosMes(idUsuario, mesAno);
        BigDecimal total = BigDecimal.ZERO;
        return despesas.stream().map(TransacaoFinanceiraEntity::getValor).reduce(total, BigDecimal::add);
    }

     public List<TransacaoFinanceiraEntity> listaPaginada(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        long totalEntidades = repository.count();
        if (totalEntidades == 0) {
            return new ArrayList<TransacaoFinanceiraEntity>();
        }
        int qntPagina = (int) Math.ceil((double) totalEntidades / tamanhoPagina);
        System.out.println("Quantidade de páginas: " + qntPagina);
        System.out.println("Página solicitada: " + pagina);
        System.out.println("pagina > qntPagina: " + (pagina > qntPagina));
        System.out.println("pagina < 1: " + (pagina < 1));
        if (pagina > qntPagina || pagina < 1) {
            throw new IllegalArgumentException("Página fora do intervalo.");
        }
        return repository.listaPaginadaComOrdenacao(pagina, tamanhoPagina, asc, usuarioId, propriedade);
    }

    public long conta(Long usuarioId) {
        return repository.conta(usuarioId);
    }
}