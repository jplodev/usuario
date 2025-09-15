package com.jpdev.usuario.business.converter;

import com.jpdev.usuario.business.dtos.EnderecoDTO;
import com.jpdev.usuario.business.dtos.TelefoneDTO;
import com.jpdev.usuario.business.dtos.UsuarioDTO;
import com.jpdev.usuario.infrastructure.entity.Endereco;
import com.jpdev.usuario.infrastructure.entity.Telefone;
import com.jpdev.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ? paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ? paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ? paraListaEnderecoDTO(usuario.getEnderecos()) : null)
                .telefones(usuario.getTelefones() != null ? paraListaTelefoneDTO(usuario.getTelefones()) : null)
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .bairro(dto.getBairro() != null ? dto.getBairro() : entity.getBairro())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .usuario_id(entity.getUsuario_id())
                .build();
    }

    public Telefone upadateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .usuario_id(entity.getUsuario_id())
                .build();
    }

    public Endereco cadastraEndereco(EnderecoDTO dto, Long id){
        return Endereco.builder()
                .id(dto.getId())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .usuario_id(id)
                .build();
    }

    public Telefone cadastraTelefone(TelefoneDTO dto, Long id){
        return Telefone.builder()
                .id(dto.getId())
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuario_id(id)
                .build();
    }
}
