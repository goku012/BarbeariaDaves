package com.example.brenobarbearia.model.repositorio;

import com.example.brenobarbearia.model.entidade.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepositorio extends JpaRepository<Agenda, Long> {
}
