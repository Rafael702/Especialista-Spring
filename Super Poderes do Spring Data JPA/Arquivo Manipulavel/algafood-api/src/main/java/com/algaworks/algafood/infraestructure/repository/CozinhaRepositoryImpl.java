package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CozinhaRepositoryImpl {
    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    public List<Cozinha> consultarPorNome(String nome) {
        return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)

                .setParameter("nome", "%" + nome + "%").getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    public Cozinha buscar(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = buscar(id);

        if (cozinha == null) {
            //Esperavamos que fosse encontrado ao menos uma cozinha com este id
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(cozinha);
    }
}
