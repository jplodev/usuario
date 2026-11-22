package com.jpdev.usuario.controller;

import com.jpdev.usuario.business.UsuarioService;
import com.jpdev.usuario.business.dtos.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable("email") String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaUsuario(@RequestBody UsuarioDTO dto,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }
}
