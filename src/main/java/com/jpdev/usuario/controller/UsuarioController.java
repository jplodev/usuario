package com.jpdev.usuario.controller;

import com.jpdev.usuario.business.UsuarioService;
import com.jpdev.usuario.business.dto.EnderecoDTO;
import com.jpdev.usuario.business.dto.TelefoneDTO;
import com.jpdev.usuario.business.dto.UsuarioDTO;
import com.jpdev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
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
    public ResponseEntity<UsuarioDTO> gravaUsuario(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.gravaUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.autenticarUsuario(dto));
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

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaUsuario(@RequestBody UsuarioDTO dto,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaUsuario(token, dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEnderecoUsuario(@RequestBody EnderecoDTO dto,
                                                               @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEnderecoUsuario(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefoneUsuario(@RequestBody TelefoneDTO dto,
                                                               @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefoneUsuario(id, dto));
    }
}
