package com.jpdev.usuario.controller;

import com.jpdev.usuario.business.UsuarioService;
import com.jpdev.usuario.business.dtos.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvaUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticaUsuario(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.autenticarUsuario(dto));
    }
}
