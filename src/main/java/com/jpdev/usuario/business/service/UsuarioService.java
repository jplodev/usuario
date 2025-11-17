package com.jpdev.usuario.business.service;

import com.jpdev.usuario.business.converter.UsuarioConverter;
import com.jpdev.usuario.business.dto.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import com.jpdev.usuario.infrastructure.exception.ConflictException;
import com.jpdev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto){
        verificaEmail(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(repository.save(usuario));
    }

    public void verificaEmail(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return repository.existsByEmail(email);
    }

}
