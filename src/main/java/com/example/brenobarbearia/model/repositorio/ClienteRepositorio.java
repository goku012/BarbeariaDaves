package com.example.brenobarbearia.model.repositorio;

import com.example.brenobarbearia.model.entidade.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
