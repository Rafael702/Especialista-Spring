package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return cadastroEstadoService.listarEstados();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable(name = "estadoId") Long id) {
        return cadastroEstadoService.buscarEstado(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvarEstado(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "estadoId") Long id, @RequestBody Estado estado) {
        try {
            Estado estadoASerAtualizado = estadoRepository.buscar(id);

            if (estadoASerAtualizado != null) {
                BeanUtils.copyProperties(estado, estadoASerAtualizado, "id");

                cadastroEstadoService.salvarEstado(estadoASerAtualizado);

                return ResponseEntity.ok(estadoASerAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity excluir(@PathVariable(name = "estadoId") Long id) {
        try {
            cadastroEstadoService.excluirEstado(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
