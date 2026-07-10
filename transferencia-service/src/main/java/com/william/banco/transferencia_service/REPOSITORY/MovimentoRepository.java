package com.william.banco.transferencia_service.REPOSITORY;


import com.william.banco.transferencia_service.ENTITY.MovimentoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentoRepository extends JpaRepository<MovimentoEntidade, Long> {
    List<MovimentoEntidade> findByContaOrigemId(Long contaOrigemId);
    List<MovimentoEntidade> findByContaDestinoId(Long contaDestinoId);
    boolean existsByNumOperacao(String numeroOperacao);
}
