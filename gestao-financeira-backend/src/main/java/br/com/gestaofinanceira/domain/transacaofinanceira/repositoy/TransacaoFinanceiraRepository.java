package br.com.gestaofinanceira.domain.transacaofinanceira.repositoy;

import java.time.LocalDate;
import java.util.List;

import br.com.gestaofinanceira.domain.transacaofinanceira.model.TransacaoFinanceiraEntity;
import br.com.gestaofinanceira.infra.enums.TipoTransacaoFinanceiraEnum;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class TransacaoFinanceiraRepository implements PanacheRepository<TransacaoFinanceiraEntity> {

    public List<TransacaoFinanceiraEntity> buscarPorMesAnoUsuario(Long usuarioId, String mesAno) {
        String[] partes = mesAno.split("/");
        int mes = Integer.parseInt(partes[0]);
        int ano = Integer.parseInt(partes[1]);
        LocalDate dataInicial = LocalDate.of(ano, mes, 1);
        LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        return find("usuario.id = ?1 AND (data >= ?2 and data <= ?3)", usuarioId, dataInicial, dataFinal).list();
    }

    public List<TransacaoFinanceiraEntity> buscarUltimasTransacoes(String descricao, int quantidadeDeRegistros) {
        return find("Upper(descricao) like ?1 order by data desc", "%" + descricao.toUpperCase() + "%")
                .page(0, quantidadeDeRegistros).list();
    }

    public List<TransacaoFinanceiraEntity> buscarTotalGastosMes(Long idUsuario, String mesAno) {
        String[] partes = mesAno.split("/");
        int mes = Integer.parseInt(partes[0]);
        int ano = Integer.parseInt(partes[1]);
        LocalDate dataInicial = LocalDate.of(ano, mes, 1);
        LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        return find("usuario.id = ?1 AND (data >= ?2 and data <= ?3) and tipo = ?4", idUsuario, dataInicial, dataFinal,
                TipoTransacaoFinanceiraEnum.DESPESA).list();
    }

    public List<TransacaoFinanceiraEntity> listaPaginadaComOrdenacao(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM TransacaoFinanceiraEntity t ");
        queryBuilder.append("WHERE t.usuario.id = :usuarioId ");
        if(propriedade != null && !propriedade.isEmpty()) {
            queryBuilder.append("ORDER BY t.").append(propriedade).append(asc ? " ASC" : " DESC");
        } else {
            queryBuilder.append("ORDER BY t.data DESC");
        }
        TypedQuery<TransacaoFinanceiraEntity> query = getEntityManager().createQuery(queryBuilder.toString(), TransacaoFinanceiraEntity.class);
        query.setParameter("usuarioId", usuarioId);
        query.setFirstResult((pagina - 1) * tamanhoPagina);
        query.setMaxResults(tamanhoPagina);
        return query.getResultList();
    }

    public Long conta(Long usuarioId) {
        TypedQuery<Long> countQuery = getEntityManager().createQuery("SELECT COUNT(t) FROM TransacaoFinanceiraEntity t WHERE t.usuario.id = :usuarioId", Long.class);
        countQuery.setParameter("usuarioId", usuarioId);
        return countQuery.getSingleResult();
    }

}