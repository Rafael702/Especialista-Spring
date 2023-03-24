package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl {
    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    public Restaurante buscar(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Transactional
    public void remover(Restaurante restaurante) {
        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }
}
