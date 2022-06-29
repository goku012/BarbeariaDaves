package com.example.brenobarbearia.model.repositorio;

import com.example.brenobarbearia.model.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}

