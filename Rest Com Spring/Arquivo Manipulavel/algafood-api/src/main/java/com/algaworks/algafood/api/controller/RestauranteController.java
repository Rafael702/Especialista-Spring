package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
