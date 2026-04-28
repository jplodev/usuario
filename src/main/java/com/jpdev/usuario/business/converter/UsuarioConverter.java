package com.jpdev.usuario.business.converter;

import com.jpdev.usuario.business.dto.EnderecoDTO;
import com.jpdev.usuario.business.dto.TelefoneDTO;
import com.jpdev.usuario.business.dto.UsuarioDTO;
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
                .enderecos(paraListaEndereco(dto.getEnderecos() != null ? dto.getEnderecos() : null))
                .telefones(paraListaTelefone(dto.getTelefones() != null ? dto.getTelefones(): null))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> dtos){
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO dto){
        return Endereco.builder()
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
        return dtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO dto){
        return Telefone.builder()
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

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> entities){
        return entities.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone entity){
        return TelefoneDTO.builder()
                .id(entity.getId())
                .ddd(entity.getDdd())
                .numero(entity.getNumero())
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

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .usuario_id(entity.getUsuario_id())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelfoneEntity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuario_id(idUsuario)
                .build();
    }
}
