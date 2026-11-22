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

    public Usuario paraUsuario(UsuarioDTO dto){
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEndereco(dto.getEnderecos()))
                .telefones(paraListaTelefone(dto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> dtos){
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO dto){
        return Endereco.builder()
                .id(dto.getId())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> dtos){
        return dtos.stream().map(this::paraTelfone).toList();
    }

    public Telefone paraTelfone(TelefoneDTO dto){
        return Telefone.builder()
                .id(dto.getId())
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario entity){
        return UsuarioDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .enderecos(paraListaEnderecoDTO(entity.getEnderecos()))
                .telefones(paraListaTelefoneDTO(entity.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> entities){
        return entities.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco entity){
        return EnderecoDTO.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .complemento(entity.getComplemento())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> etities){
        return etities.stream().map(this::paraTelfoneDTO).toList();
    }

    public TelefoneDTO paraTelfoneDTO(Telefone entity){
        return TelefoneDTO.builder()
                .id(entity.getId())
                .ddd(entity.getDdd())
                .numero(entity.getNumero())
                .build();
    }
}
