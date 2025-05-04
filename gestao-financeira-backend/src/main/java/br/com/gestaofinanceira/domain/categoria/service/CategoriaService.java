package br.com.gestaofinanceira.domain.categoria.service;

import java.util.List;

import br.com.gestaofinanceira.domain.categoria.model.CategoriaEntity;
import br.com.gestaofinanceira.domain.categoria.repositoy.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository repository;

    public List<CategoriaEntity> listarTodas() {
        return repository.listAll();
    }

    @Transactional
    public void salvar(CategoriaEntity entity) {
        repository.persist(entity);
    }

    public CategoriaEntity buscarPorId(Long id) {
        return repository.findById(id);
    }

}