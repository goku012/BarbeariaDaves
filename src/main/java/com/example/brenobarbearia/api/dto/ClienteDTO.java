package com.example.brenobarbearia.api.dto;

import com.example.brenobarbearia.model.entidade.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDTO {

    private Long id;
    private String nome;
    private String empresa;
    private String cargo;
    private String telefone;
    private String email;

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
