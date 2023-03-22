package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infraestructure.repository.RestauranteRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> listarRestaurantes(){
        return restauranteRepository.listar();
    }

    public Restaurante buscarRestaurante(Long id){
        Restaurante restaurante = restauranteRepository.buscar(id);

        if (restaurante == null) {
            throw new EntidadeNaoEncontradaException(String.format(
                    "Nao existe um cadastro de cozinha com o codigo: %d", id
            ));
        }

        return restaurante;
    }
}
