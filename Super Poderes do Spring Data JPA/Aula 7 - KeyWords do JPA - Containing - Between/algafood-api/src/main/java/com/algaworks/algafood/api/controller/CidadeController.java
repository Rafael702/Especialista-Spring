package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cadastroCidadeService.listarCidade();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable(name = "cidadeId") Long id) {
        try {
            Cidade cidade = cadastroCidadeService.buscarCidade(id);
            return ResponseEntity.ok(cidade);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return cadastroCidadeService.salvarCidade(cidade);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<Cidade> atualizar(@PathVariable(name = "cidadeId") Long id, @RequestBody Cidade cidade) {
        try {
            Cidade cidadeASerAtualizada = cadastroCidadeService.buscarCidade(id);

            BeanUtils.copyProperties(cidade, cidadeASerAtualizada, "id");
            cadastroCidadeService.salvarCidade(cidadeASerAtualizada);

            return ResponseEntity.ok(cidadeASerAtualizada);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> deletar(@PathVariable(name = "cidadeId") Long id) {
        try {
            cadastroCidadeService.excluirCidade(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
