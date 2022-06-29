package com.example.brenobarbearia.api.controller;

import com.example.brenobarbearia.api.dto.EquipeDTO;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.Equipe;
import com.example.brenobarbearia.service.EquipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/equ")
@RequiredArgsConstructor

public class EquipeController {
    private final EquipeService service;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(equipe.map(EquipeDTO::create));
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Equipe> equipes = service.getEquipes();
        return ResponseEntity.ok(equipes.stream().map(EquipeDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(EquipeDTO dto) {
        try {
            Equipe equipe = converter(dto);
            equipe = service.salvar(equipe);
            return new ResponseEntity(equipe, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, EquipeDTO dto) {
        if (!service.getEquipeById(id).isPresent()) {
            return new ResponseEntity("não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Equipe equipe = converter(dto);
            equipe.setId(id);
            service.salvar(equipe);
            return ResponseEntity.ok(equipe);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Equipe converter(EquipeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Equipe equipe = modelMapper.map(dto, Equipe.class);
        return equipe;
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(equipe.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
