package com.algaworks.algafood.infraestructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        return new RestauranteComFreteGratisSpec();
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return new RestauranteComNomeSemelhanteSpec(nome);
    }

}
