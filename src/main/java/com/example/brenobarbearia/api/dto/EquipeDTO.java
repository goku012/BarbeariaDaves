package com.example.brenobarbearia.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.brenobarbearia.model.entidade.Equipe;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EquipeDTO {

    private Long id;
    private String nome;
    private String especialidade;
    private int idade;
    private String descricao;


    public static EquipeDTO create(Equipe equipe) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(equipe, EquipeDTO.class);
    }
}
