package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = cadastroRestauranteService.listarRestaurantes();
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable(name = "restauranteId") Long id) {
        try {
            Restaurante restaurante = cadastroRestauranteService.buscarRestaurante(id);
            return ResponseEntity.ok().body(restaurante);
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restauranteASerSalvo) {
        try {
            Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteASerSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "restauranteId") Long id, @RequestBody Restaurante updateRestaurante) {
        try {
            Restaurante restauranteASerAtualizado = restauranteRepository.buscar(id);

            if (restauranteASerAtualizado != null) {
                BeanUtils.copyProperties(updateRestaurante, restauranteASerAtualizado, "id");

                Restaurante restauranteAtualizado = cadastroRestauranteService.salvar(restauranteASerAtualizado);

                return ResponseEntity.status(HttpStatus.OK).body(restauranteAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizaParcial(@PathVariable(name = "restauranteId") Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = restauranteRepository.buscar(id);

        if (restauranteAtual == null) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual);

        return atualizar(id, restauranteAtual);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            field.setAccessible(true);
            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });

    }


}
