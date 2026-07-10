package com.william.banco.conta_service.SERVICE;

import com.william.banco.conta_service.DTO.ContaAtualizarSaldoRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestSaldoDTO;
import com.william.banco.conta_service.DTO.ContaResponseDTO;
import com.william.banco.conta_service.ENTITY.ContaEntidade;
import com.william.banco.conta_service.ENTITY.StatusConta;
import com.william.banco.conta_service.REPOSITORY.ContaRepository;
//import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class ContaService  implements IContaService{

    @Autowired
    private ContaRepository contaRepository;

    @Override
    public ContaResponseDTO criarConta(ContaRequestDTO dto) {

        String numeroConta;

        do {
            numeroConta = gerarNumeroConta();
        } while (contaRepository.existsByNumeroConta(numeroConta));

        ContaEntidade conta = new ContaEntidade();
        conta.setClienteId(dto.getClienteId());
        conta.setTipoconta(dto.getTipoconta());
        conta.setSaldo(dto.getValor());
        conta.setNumeroConta(numeroConta);

        conta = contaRepository.save(conta);

        return toResponseDTO(conta);
    }

    @Override
    public List<ContaResponseDTO> listarContas() {
        return contaRepository.findAll()
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContaResponseDTO atualizarSaldo(ContaAtualizarSaldoRequestDTO dto) {
        ContaEntidade conta = contaRepository.findByNumeroConta(dto.getNumeroConta()).
                orElseThrow(() -> new RuntimeException("Conta Inexistente"));

        conta.setSaldo(conta.getSaldo().add(dto.getValor())); // soma — positivo credita, negativo debita
        return toResponseDTO(contaRepository.save(conta));
    }

    @Override
    public ContaResponseDTO desativarConta(Long id) {
        ContaEntidade conta = contaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Coonta não encontrada"));;

        /*if(conta.getStatus().equals(StatusConta.ATIVA)){
            conta.setStatus(StatusConta.INATIVA);
        }*/
        conta.setStatus(StatusConta.INATIVA);
        return toResponseDTO(contaRepository.save(conta));
    }

    @Override
    public ContaResponseDTO ativarConta(Long id) {
        ContaEntidade conta = contaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Conta não encontrada"));;

        conta.setStatus(StatusConta.ATIVA);
        return toResponseDTO(contaRepository.save(conta));
    }

    @Override
    public ContaResponseDTO buscarPorId(Long id) {
        ContaEntidade conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
        return toResponseDTO(conta);
    }

    @Override
    public List<ContaResponseDTO> buscarPeloClienteID(Long id) {
        return contaRepository.findByClienteId(id)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContaResponseDTO buscarPorNumeroConta(String numeroConta) {
        ContaEntidade conta = contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta " + numeroConta + " não encontrada"));
        return toResponseDTO(conta);
    }


    private String gerarNumeroConta() {
        return java.util.UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10).toUpperCase();
    }

    private ContaResponseDTO toResponseDTO(ContaEntidade conta) {
        ContaResponseDTO dto = new ContaResponseDTO();
        dto.setId(conta.getId());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setSaldo(conta.getSaldo());
        dto.setTipoConta(conta.getTipoconta());
        dto.setDataCriacao(conta.getDataCriacao());
        dto.setStatus(conta.getStatus());
        dto.setClienteId(conta.getClienteId());
        return dto;
    }
}
