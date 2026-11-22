package com.jpdev.usuario.business;

import com.jpdev.usuario.business.converter.UsuarioConverter;
import com.jpdev.usuario.business.dtos.EnderecoDTO;
import com.jpdev.usuario.business.dtos.TelefoneDTO;
import com.jpdev.usuario.business.dtos.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Endereco;
import com.jpdev.usuario.infrastructure.entity.Telefone;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import com.jpdev.usuario.infrastructure.exceptions.ConflictException;
import com.jpdev.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.jpdev.usuario.infrastructure.repository.EnderecoRepository;
import com.jpdev.usuario.infrastructure.repository.TelefoneRepositoy;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepositoy telefoneRepositoy;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto){
        emailExiste(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public String autenticarUsuario(UsuarioDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Eroo, email já cadastrado " + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Erro, email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        try{
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado ", e.getCause());
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone( Long idTelefone, TelefoneDTO dto){
        Telefone entity = telefoneRepositoy.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idTelefone));
        Telefone telefone = usuarioConverter.updatTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepositoy.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado"));
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
        Telefone telefone = usuarioConverter.paraTelfoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepositoy.save(telefone));
    }
}
