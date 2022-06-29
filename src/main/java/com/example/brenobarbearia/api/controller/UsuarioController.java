package com.example.brenobarbearia.api.controller;

import com.example.brenobarbearia.model.entidade.Usuario;
import com.example.brenobarbearia.security.JwtService;
import com.example.brenobarbearia.service.UsuarioService;
import com.example.brenobarbearia.api.dto.CredenciaisDTO;
import com.example.brenobarbearia.api.dto.TokenDTO;
import com.example.brenobarbearia.exception.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UsuarioController {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario ){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }
}
