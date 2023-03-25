package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        //clausula
        criteria.from(Restaurante.class); //from Restaurante

        TypedQuery<Restaurante> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }
}
