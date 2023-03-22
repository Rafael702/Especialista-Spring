package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.remover(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Nao existe um cadastro de cozinha com o codigo: %d", id
                    )
            );
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(
                            "Cozinha de codigo %d nao pode ser removida, pois esta em uso", id
                    )
            ); //Exception de Negocio'
        }
    }

    public List<Cozinha> listarCozinhas() {
        return cozinhaRepository.listar();
    }

    public Cozinha buscarCozinha(Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);

        if (cozinha == null) {
            throw new EntidadeNaoEncontradaException(String.format(
                    "Nao existe um cadastro de cozinha com o codigo: %d", id
            ));
        }

        return cozinha;
    }

    public Cozinha atualizarCozinha(Long id, Cozinha updateCozinha) {
        Cozinha cozinhaASerAtualizada = buscarCozinha(id);

        BeanUtils.copyProperties(cozinhaASerAtualizada, updateCozinha);

        return cozinhaRepository.salvar(cozinhaASerAtualizada);
    }
}
