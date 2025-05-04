package br.com.gestaofinanceira.domain.categoria.repositoy;

import br.com.gestaofinanceira.domain.categoria.model.CategoriaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<CategoriaEntity> { }