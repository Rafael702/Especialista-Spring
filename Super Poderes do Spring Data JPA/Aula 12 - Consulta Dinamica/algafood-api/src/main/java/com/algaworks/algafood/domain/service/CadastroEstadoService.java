package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listarEstados() {
        return estadoRepository.findAll();
    }

    public Estado buscarEstado(Long id) {
        Estado estado = estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(
                        "Nao existe um cadastro de estado com o codigo: %d", id
                )
        ));

        return estado;
    }

    public Estado salvarEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluirEstado(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(String.format(
                    "Nao existe um cadastro de Estado com o codigo: %d", id
            ));
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format(
                    "Estado - de codigo %d nao pode ser removida, pois esta em uso", id
            ));
        }
    }

}
