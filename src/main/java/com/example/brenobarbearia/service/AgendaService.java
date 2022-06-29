package com.example.brenobarbearia.service;
import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.*;
import com.example.brenobarbearia.model.repositorio.AgendaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendaService {

    private AgendaRepositorio repository;

    public AgendaService(AgendaRepositorio repository) {
        this.repository = repository;
    }

    public List<Agenda> getAgenda() {
        return repository.findAll();
    }

    public Optional<Agenda> getAgendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Agenda salvar(Agenda agenda) {
        validar(agenda);
        return repository.save(agenda);
    }

    @Transactional
    public void excluir(Agenda agenda) {
        Objects.requireNonNull(agenda.getId());
        repository.delete(agenda);
    }

    public void validar(Agenda agenda) {
        if (agenda.getData() == null) {
            throw new RegraNegocioException("Data inválida");
        }
        if (agenda.getCliente() == null || agenda.getCliente().getId() == null || agenda.getCliente().getId() == 0) {
            throw new RegraNegocioException("Cliente inválido");
        }
        if (agenda.getEquipe() == null || agenda.getEquipe().getId() == null || agenda.getEquipe().getId() == 0) {
            throw new RegraNegocioException("Equipe inválida");
        }
        if (agenda.getServico() == null || agenda.getServico().getId() == null || agenda.getServico().getId() == 0) {
            throw new RegraNegocioException("Servico inválido");
        }
    }
}
