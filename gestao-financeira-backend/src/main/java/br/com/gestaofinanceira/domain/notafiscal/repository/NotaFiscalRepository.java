package br.com.gestaofinanceira.domain.notafiscal.repository;

import java.util.List;

import br.com.gestaofinanceira.domain.notafiscal.model.NotaFiscalEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class NotaFiscalRepository  implements PanacheRepository<NotaFiscalEntity> {

    public List<NotaFiscalEntity> listaPaginadaComOrdenacao(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM NotaFiscalEntity t ");
        queryBuilder.append("WHERE t.usuario.id = :usuarioId ");
        if(propriedade != null && !propriedade.isEmpty()) {
            queryBuilder.append("ORDER BY t.").append(propriedade).append(asc ? " ASC" : " DESC");
        } else {
            queryBuilder.append("ORDER BY t.data DESC");
        }
        TypedQuery<NotaFiscalEntity> query = getEntityManager().createQuery(queryBuilder.toString(), NotaFiscalEntity.class);
        query.setParameter("usuarioId", usuarioId);
        query.setFirstResult((pagina - 1) * tamanhoPagina);
        query.setMaxResults(tamanhoPagina);
        return query.getResultList();
    }

    public Long conta(Long usuarioId) {
        TypedQuery<Long> countQuery = getEntityManager().createQuery("SELECT COUNT(t) FROM NotaFiscalEntity t WHERE t.usuario.id = :usuarioId", Long.class);
        countQuery.setParameter("usuarioId", usuarioId);
        return countQuery.getSingleResult();
    }
}
