package com.jpdev.usuario.business;

import com.jpdev.usuario.business.converter.UsuarioConverter;
import com.jpdev.usuario.business.dto.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import com.jpdev.usuario.infrastructure.exceptions.ConflictException;
import com.jpdev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaDadosUsuario(UsuarioDTO usuarioDTO){
        emailExistente(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    public void emailExistente(String email){
        try{
           boolean existe = verificaEmailExistente(email);
           if (existe){
               throw  new ConflictException("Email já cadastrado " + email);
           }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }
}
