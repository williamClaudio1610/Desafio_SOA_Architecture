package com.william.banco.transferencia_service.SERVCE;

import com.william.banco.transferencia_service.DTO.MovimentoRequestDTO;
import com.william.banco.transferencia_service.DTO.MovimentoResponseDTO;
import com.william.banco.transferencia_service.ENTITY.ENUM.TipoMovimento;
import com.william.banco.transferencia_service.ENTITY.MovimentoEntidade;
import com.william.banco.transferencia_service.REPOSITORY.MovimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentoService implements IMovimentoService{

    @Autowired
    private MovimentoRepository movimentoRepository;


    @Override
    public MovimentoResponseDTO depositar(MovimentoRequestDTO dto) {

        String numOperacao;

        do{
            numOperacao = gerarNumOperacao();
        }while(movimentoRepository.existsByNumOperacao(numOperacao));

        MovimentoEntidade movimento = new MovimentoEntidade();
        movimento.setTipoMovimento(TipoMovimento.DEPOSITO);
        movimento.setValor(dto.getValor());
        movimento.setContaOrigemId(dto.getContaOrigemId());
        movimento.setDescricao(dto.getDescricao());
        movimento.setNumOperacao(numOperacao);
        movimento.setContaDestinoId(dto.getContaDestinoId());

        return toResponseDTO(movimentoRepository.save(movimento));
    }

    @Override
    public MovimentoResponseDTO levantar(MovimentoRequestDTO dto) {

        String numOperacao;
        do{
            numOperacao = gerarNumOperacao();
        }while(movimentoRepository.existsByNumOperacao(numOperacao));

        MovimentoEntidade movimento = new MovimentoEntidade();
        movimento.setTipoMovimento(TipoMovimento.LEVANTAMENTO);
        movimento.setValor(dto.getValor());
        movimento.setContaOrigemId(dto.getContaOrigemId());
        movimento.setDescricao(dto.getDescricao());
        movimento.setNumOperacao(numOperacao);
        movimento.setContaOrigemId(dto.getContaOrigemId());

        return toResponseDTO(movimentoRepository.save(movimento));
    }

    @Override
    public MovimentoResponseDTO transferir(MovimentoRequestDTO dto) {

        String numOperacao;
        do{
            numOperacao = gerarNumOperacao();
        }while(movimentoRepository.existsByNumOperacao(numOperacao));

        MovimentoEntidade transfer = new MovimentoEntidade();
        transfer.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
        transfer.setValor(dto.getValor());
        transfer.setContaOrigemId(dto.getContaOrigemId());
        transfer.setContaDestinoId(dto.getContaDestinoId());
        transfer.setDescricao(dto.getDescricao());
        transfer.setNumOperacao(numOperacao);
        movimentoRepository.save(transfer);

        return toResponseDTO(movimentoRepository.save(transfer));
    }

    @Override
    public List<MovimentoResponseDTO> listarPorConta(Long contaId) {
        List<MovimentoEntidade> movimentos = new ArrayList<>();
        movimentos.addAll(movimentoRepository.findByContaOrigemId(contaId));
        movimentos.addAll(movimentoRepository.findByContaDestinoId(contaId));
        return movimentos.stream()
                .sorted(Comparator.comparing(MovimentoEntidade::getDataMovimento).reversed())
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private MovimentoResponseDTO toResponseDTO(MovimentoEntidade mov) {
        MovimentoResponseDTO dto = new MovimentoResponseDTO();
        dto.setId(mov.getId());
        dto.setTipoMovimento(mov.getTipoMovimento());
        dto.setValor(mov.getValor());
        dto.setContaOrigemId(mov.getContaOrigemId());
        dto.setContaDestinoId(mov.getContaDestinoId());
        dto.setDataMovimento(mov.getDataMovimento());
        dto.setDescricao(mov.getDescricao());
        dto.setEstadoMovimento(mov.getStatus());
        dto.setNumOperacao(mov.getNumOperacao());
        return dto;
    }

    private String gerarNumOperacao() {
        long numero = (long) (Math.random() * 1_000_000_0000L);
        return String.format("%010d", numero);
    }
}
