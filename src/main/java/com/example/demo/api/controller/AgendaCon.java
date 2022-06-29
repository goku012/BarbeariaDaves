package com.example.demo.api.controller;

import com.example.demo.api.dto.AgendaDTO;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Agenda;
import com.example.demo.service.AgendaService;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EquipeService;
import com.example.demo.service.ServicoService;
import com.example.demo.model.entity.Cliente;
import com.example.demo.model.entity.Equipe;
import com.example.demo.model.entity.Servico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import io.swagger.annotations.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor
@Api("API de Agenda")
public class AgendaCon {

    private final AgendaService service;
    private final ClienteService clienteService;
    private final EquipeService equipeService;
    private final ServicoService servicoService;


    @GetMapping()
    public ResponseEntity get() {
        List<Agenda> agendas = service.getAgenda();
        return ResponseEntity.ok(agendas.stream().map(AgendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Agenda> agenda = service.getAgendaById(id);
        if (!agenda.isPresent()) {
            return new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agenda.map(AgendaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(AgendaDTO dto) {
        try {
            Agenda agenda = converter(dto);
            agenda = service.salvar(agenda);
            return new ResponseEntity(agenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, AgendaDTO dto) {
        if (!service.getAgendaById(id).isPresent()) {
            return new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Agenda agenda = converter(dto);
            agenda.setId(id);
            service.salvar(agenda);
            return ResponseEntity.ok(agenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Agenda> agenda = service.getAgendaById(id);
        if (!agenda.isPresent()) {
            return new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(agenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Agenda converter(AgendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Agenda agenda = modelMapper.map(dto, Agenda.class);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                agenda.setCliente(null);
            } else {
                agenda.setCliente(cliente.get());
            }
        }
        if (dto.getIdEquipe() != null) {
            Optional<Equipe> equipe = equipeService.getEquipeById(dto.getIdEquipe());
            if (!equipe.isPresent()) {
                agenda.setEquipe(null);
            } else {
                agenda.setEquipe(equipe.get());
            }
        }
        if (dto.getIdServico() != null) {
            Optional<Servico> servico = servicoService.getServicoById(dto.getIdServico());
            if (!servico.isPresent()) {
                agenda.setServico(null);
            } else {
                agenda.setServico(servico.get());
            }
        }
        return agenda;
    }
}
