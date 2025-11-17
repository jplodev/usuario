package com.jpdev.usuario.business.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TelefoneDTO {

    private Long id;
    private String ddd;
    private String numero;
}
