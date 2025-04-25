package com.jpdev.usuario.business.dtos;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO> enderecoDTOS;
    private List<TelefoneDTO> telefoneDTOS;
}
