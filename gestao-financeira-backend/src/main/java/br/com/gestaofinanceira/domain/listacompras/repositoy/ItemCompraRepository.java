package br.com.gestaofinanceira.domain.listacompras.repositoy;

import java.util.List;

import br.com.gestaofinanceira.domain.listacompras.model.ItemCompraEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompraEntity> {

    public List<ItemCompraEntity> listarPorUsuario(Long usuarioId) {
        return find("usuario.id", usuarioId).list();
    } 


    public List<ItemCompraEntity> listaPaginadaComOrdenacao(int pagina, int tamanhoPagina, boolean asc, Long usuarioId, String propriedade) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM ItemCompraEntity t ");
        queryBuilder.append("WHERE t.usuario.id = :usuarioId ");
        if(propriedade != null && !propriedade.isEmpty()) {
            queryBuilder.append("ORDER BY t.").append(propriedade).append(asc ? " ASC" : " DESC");
        } else {
            queryBuilder.append("ORDER BY t.id DESC");
        }
        TypedQuery<ItemCompraEntity> query = getEntityManager().createQuery(queryBuilder.toString(), ItemCompraEntity.class);
        query.setParameter("usuarioId", usuarioId);
        query.setFirstResult((pagina - 1) * tamanhoPagina);
        query.setMaxResults(tamanhoPagina);
        return query.getResultList();
    }

    public Long conta(Long usuarioId) {
        TypedQuery<Long> countQuery = getEntityManager().createQuery("SELECT COUNT(t) FROM ItemCompraEntity t WHERE t.usuario.id = :usuarioId", Long.class);
        countQuery.setParameter("usuarioId", usuarioId);
        return countQuery.getSingleResult();
    }

}