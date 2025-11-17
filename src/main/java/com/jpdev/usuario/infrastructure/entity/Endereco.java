package com.jpdev.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua", length = 100)
    private String rua;
    @Column(name = "numero", length = 30)
    private String numero;
    @Column(name = "bairro", length = 100)
    private String bairro;
    @Column(name = "cidade", length = 100)
    private String cidade;
    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "cep", length = 10)
    private String cep;
    @Column(name = "complemento", length = 100)
    private String complemento;
}
