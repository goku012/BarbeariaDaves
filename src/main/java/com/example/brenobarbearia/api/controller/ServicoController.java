package com.example.brenobarbearia.api.controller;

import com.example.brenobarbearia.api.dto.ServicoDTO;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.Servico;
import com.example.brenobarbearia.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/serv")
@RequiredArgsConstructor

public class ServicoController {
    private final ServicoService service;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Servico> servicos = service.getServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(ServicoDTO dto) {
        try {
            Servico servico = converter(dto);
            servico = service.salvar(servico);
            return new ResponseEntity(servico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ServicoDTO dto) {
        if (!service.getServicoById(id).isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Servico servico = converter(dto);
            servico.setId(id);
            service.salvar(servico);
            return ResponseEntity.ok(servico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        return servico;
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(servico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

