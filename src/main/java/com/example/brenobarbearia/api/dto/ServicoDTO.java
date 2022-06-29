package com.example.brenobarbearia.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.brenobarbearia.model.entidade.Servico;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ServicoDTO {

    private Long id;
    private String nome;
    private float preco;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(servico, ServicoDTO.class);
    }
}
