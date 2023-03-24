package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
    List<Cozinha> findAllByNomeContaining(String nome);

//    @Query("from Cozinha where nome like %:nome%")
    Optional<Cozinha> consultarPorNome(String nome);

    boolean existsByNome(String nome);
}
