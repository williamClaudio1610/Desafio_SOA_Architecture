package com.william.banco.conta_service.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import com.william.banco.conta_service.ENTITY.ContaEntidade;
import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaEntidade, Long> {
    List<ContaEntidade> findByClienteId(Long clienteId);
    boolean existsByNumeroConta(String numeroConta);
    Optional<ContaEntidade> findByNumeroConta(String numeroConta);
}
