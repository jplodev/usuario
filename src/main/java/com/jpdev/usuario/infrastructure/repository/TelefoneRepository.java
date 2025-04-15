package com.jpdev.usuario.infrastructure.repository;

import com.jpdev.usuario.infrastructure.entities.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
