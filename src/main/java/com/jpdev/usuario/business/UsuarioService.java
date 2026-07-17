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
import com.jpdev.usuario.infrastructure.repository.EnderecoRespository;
import com.jpdev.usuario.infrastructure.repository.TelefoneRepository;
import com.jpdev.usuario.infrastructure.repository.UsuarioRepository;
import com.jpdev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRespository enderecoRespository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioDTO gravaUsuario(UsuarioDTO dto){
        emailExistente(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExistente(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public String autenticarUsuario(UsuarioDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaUsuario(String token, UsuarioDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getEmail()) : null );
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEnderecoUsuario(Long id, EnderecoDTO dto){
        Endereco enderecoEntity = enderecoRespository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id));
        Endereco endereco = usuarioConverter.updateEndereco(dto, enderecoEntity);
        return usuarioConverter.paraEnderecoDTO(enderecoRespository.save(endereco));
    }

    public TelefoneDTO atualizaTelefoneUsuario(Long id, TelefoneDTO dto){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id));
        Telefone telefone = usuarioConverter.updateTelefone(dto, telefoneEntity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
