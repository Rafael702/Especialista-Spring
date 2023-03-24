package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listarCidade() {
        return cidadeRepository.listar();
    }

    public Cidade buscarCidade(Long id) {
        Cidade cidade = cidadeRepository.buscar(id);

        if (cidade == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Nao existe um cadastro de cidade com o codigo: %d", id
                    )
            );
        }

        return cidade;
    }

    public Cidade salvarCidade(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

    public void excluirCidade(Long id) {
        try {
            cidadeRepository.remover(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Nao existe um cadastro de cidade com o codigo: %d", id
                    )
            );
        }
    }
}
