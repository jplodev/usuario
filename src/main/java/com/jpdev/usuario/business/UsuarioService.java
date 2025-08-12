package com.jpdev.usuario.business;

import com.jpdev.usuario.business.converter.UsuarioConverter;
import com.jpdev.usuario.business.dto.EnderecoDTO;
import com.jpdev.usuario.business.dto.TelefoneDTO;
import com.jpdev.usuario.business.dto.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Endereco;
import com.jpdev.usuario.infrastructure.entity.Telefone;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import com.jpdev.usuario.infrastructure.exceptions.ConflictException;
import com.jpdev.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.jpdev.usuario.infrastructure.repository.EnderecoRepository;
import com.jpdev.usuario.infrastructure.repository.TelefoneRepository;
import com.jpdev.usuario.infrastructure.repository.UsuarioRepository;
import com.jpdev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExistente(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public void emailExistente(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email){
       try{
           return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() ->
                   new ResourceNotFoundException("Email não encontrado " + email)));
       } catch (ResourceNotFoundException e) {
           throw new ResourceNotFoundException("Email não encontrado", e.getCause());
       }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaUsuarioPorEmail(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));
        Usuario usuario = usuarioConverter.updateusuario(usuarioDTO, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEnderecoPorId(Long id, EnderecoDTO enderecoDTO ){
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefonePorId(Long id, TelefoneDTO telefoneDTO){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
