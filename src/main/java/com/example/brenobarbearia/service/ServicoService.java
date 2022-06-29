package com.example.brenobarbearia.service;

import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.*;
import com.example.brenobarbearia.model.repositorio.ServicoRepositorio;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServicoService {
    private ServicoRepositorio repository;

    public ServicoService(ServicoRepositorio repository) {
        this.repository = repository;
    }

    public List<Servico> getServicos() {
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Servico salvar(Servico servico) {
        validar(servico);
        return repository.save(servico);
    }

    @Transactional
    public void excluir(Servico servico) {
        Objects.requireNonNull(servico.getId());
        repository.delete(servico);
    }

    public void validar(Servico servico) {
        if (servico.getNome() == null || servico.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }

}

