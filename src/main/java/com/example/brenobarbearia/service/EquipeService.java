package com.example.brenobarbearia.service;
import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.*;
import com.example.brenobarbearia.model.repositorio.EquipeRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EquipeService {

    private EquipeRepositorio repository;

    public EquipeService(EquipeRepositorio repository) {
        this.repository = repository;
    }

    public List<Equipe> getEquipes() {
        return repository.findAll();
    }

    public Optional<Equipe> getEquipeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Equipe salvar(Equipe equipe) {
        validar(equipe);
        return repository.save(equipe);
    }

    @Transactional
    public void excluir(Equipe equipe) {
        Objects.requireNonNull(equipe.getId());
        repository.delete(equipe);
    }

    public void validar(Equipe equipe) {
        if (equipe.getNome() == null || equipe.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }

}
