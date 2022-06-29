package com.example.brenobarbearia.api.controller;

import com.example.brenobarbearia.api.dto.ClienteDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.brenobarbearia.exception.RegraNegocioException;
import com.example.brenobarbearia.model.entidade.Cliente;
import com.example.brenobarbearia.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cli")

public class ClienteController {
    private final ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }
    @GetMapping()
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity post(ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        return cliente;
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
