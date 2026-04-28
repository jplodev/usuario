package com.jpdev.usuario.business;

import com.jpdev.usuario.business.converter.UsuarioConverter;
import com.jpdev.usuario.business.dto.EnderecoDTO;
import com.jpdev.usuario.business.dto.TelefoneDTO;
import com.jpdev.usuario.business.dto.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Endereco;
import com.jpdev.usuario.infrastructure.entity.Telefone;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import com.jpdev.usuario.infrastructure.exceptions.ConflictException;
import com.jpdev.usuario.infrastructure.exceptions.ResourceNotFoundExceptions;
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

    public UsuarioDTO salvaUsuario(UsuarioDTO dto){
        existeEmail(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void existeEmail(String email){
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
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundExceptions("Email não encontrado " + email)));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundExceptions("Email não encontrado " + email));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto){
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundExceptions("Id não encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(dto, enderecoEntity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundExceptions("Id não encontrado " + idTelefone));
        Telefone telefone = usuarioConverter.updateTelefone(dto, telefoneEntity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
