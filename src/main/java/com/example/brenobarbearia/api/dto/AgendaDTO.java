package com.example.brenobarbearia.api.dto;

import com.example.brenobarbearia.model.entidade.Agenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AgendaDTO {

    private String descricao;
    private Long idCliente;
    private Long idEquipe;
    private Long idServico;
    private Long id;
    private String data;
    private String horario;

    public static AgendaDTO create(Agenda agenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaDTO.class);
    }

}
