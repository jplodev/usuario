package com.jpdev.usuario.infrastructure.repository;

import com.jpdev.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRespository extends JpaRepository<Telefone, Long> {
}
