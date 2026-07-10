package com.william.banco.cliente_service.REPOSITORY;

import com.william.banco.cliente_service.ENTITY.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}