package com.jpdev.usuario.controller;

import com.jpdev.usuario.business.UsuarioService;
import com.jpdev.usuario.business.dtos.UsuarioDTO;
import com.jpdev.usuario.infrastructure.security.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.salvaUsuario(dto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable("email") String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
