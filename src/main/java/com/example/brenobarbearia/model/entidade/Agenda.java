package com.example.brenobarbearia.model.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private String horario;
    private String descricao;


    @ManyToOne
    private Servico servico;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Equipe equipe;

}
