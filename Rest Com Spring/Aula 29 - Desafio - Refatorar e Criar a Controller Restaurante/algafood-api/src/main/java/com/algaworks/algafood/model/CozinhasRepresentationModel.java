package com.algaworks.algafood.model;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasRepresentationModel {

    @JsonProperty("cozinha")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull
    private List<Cozinha> cozinha;
}
