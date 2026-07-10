package com.orquestrador.service.SERVICE;


import com.orquestrador.service.DTO.ContaComMovimentosDTO;
import com.orquestrador.service.DTO.Requests.*;
import com.orquestrador.service.DTO.Responses.*;
import com.orquestrador.service.ENUM.TipoMovimento;
import com.orquestrador.service.VALIDADOR.ClienteValidador;
import com.orquestrador.service.VALIDADOR.ContaValidador;
import com.orquestrador.service.VALIDADOR.MovimentoValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrquestradorService implements IOrquestradorService {


    @Autowired private RestTemplate restTemplate;
    @Autowired private ContaValidador contaValidator;
    @Autowired private ClienteValidador clienteValidator;
    @Autowired private MovimentoValidador movimentoValidator;

    @Value("${cliente.service.url}") private String clienteServiceUrl;
    @Value("${conta.service.url}") private String contaServiceUrl;
    @Value("${movimentos.service.url}") private String movimentosServiceUrl;

// ─── CRIAR CLIENTE (+ CONTA + DEPÓSITO INICIAL) ────────────────────────

    @Override
    public OnboardingResponseDTO criarCliente(OnboardingRequestDTO dto) {

        ClienteRequestDTO clienteRequest = new ClienteRequestDTO();
        clienteRequest.setNome(dto.getNome());
        clienteRequest.setEmail(dto.getEmail());
        clienteRequest.setTelefone(dto.getTelefone());

        ClienteResponseDTO clienteCriado = restTemplate.postForObject(
                clienteServiceUrl, clienteRequest, ClienteResponseDTO.class);

        ContaRequestDTO contaRequest = new ContaRequestDTO();
        contaRequest.setClienteId(clienteCriado.getId());
        contaRequest.setTipoConta(dto.getTipoConta());
        contaRequest.setValor(dto.getDepositoInicial());

        ContaResponseDTO contaCriada = restTemplate.postForObject(
                contaServiceUrl + "/criarConta", contaRequest, ContaResponseDTO.class);

        MovimentoRequestDTO movimentoRequest = new MovimentoRequestDTO();
        movimentoRequest.setTipoMovimento(TipoMovimento.DEPOSITO);
        movimentoRequest.setValor(dto.getDepositoInicial());
        movimentoRequest.setContaOrigemId(contaCriada.getNumeroConta());
        movimentoRequest.setDescricao("Depósito inicial");

        restTemplate.postForObject(
                movimentosServiceUrl + "/depositar", movimentoRequest, Object.class);

        OnboardingResponseDTO response = new OnboardingResponseDTO();
        response.setClienteId(clienteCriado.getId());
        response.setNomeCliente(clienteCriado.getNome());
        response.setContaId(contaCriada.getId());
        response.setNumeroConta(contaCriada.getNumeroConta());
        response.setSaldo(contaCriada.getSaldo());
        return response;
    }

    // ─── MOVIMENTOS ──────────────────────────────────────────────────────────

    @Override
    public MovimentoResponseDTO realizarMovimento(MovimentoRequestDTO dto) {
        return switch (dto.getTipoMovimento()) {
            case DEPOSITO -> depositar(dto);
            case LEVANTAMENTO -> levantar(dto);
            case TRANSFERENCIA-> transferir(dto);
        };
    }

    private MovimentoResponseDTO depositar(MovimentoRequestDTO dto) {
        movimentoValidator.validarSemContaDestino(dto);
        ContaResponseDTO conta = contaValidator.validarContaExistePorId(dto.getContaOrigemId());
        contaValidator.validarContaAtiva(conta);

        MovimentoResponseDTO movimento = restTemplate.postForObject(
                movimentosServiceUrl + "/depositar", dto, MovimentoResponseDTO.class);

        atualizarSaldoConta(conta.getNumeroConta(), dto.getValor());
        return movimento;
    }

    private MovimentoResponseDTO levantar(MovimentoRequestDTO dto) {
        movimentoValidator.validarSemContaDestino(dto);
        ContaResponseDTO conta = contaValidator.validarContaExistePorId(dto.getContaOrigemId());
        contaValidator.validarContaAtiva(conta);
        contaValidator.validarSaldoSuficiente(conta, dto.getValor());

        MovimentoResponseDTO movimento = restTemplate.postForObject(
                movimentosServiceUrl + "/levantar", dto, MovimentoResponseDTO.class);

        atualizarSaldoConta(dto.getContaOrigemId(), dto.getValor().negate());
        return movimento;
    }

    private MovimentoResponseDTO transferir(MovimentoRequestDTO dto) {
        movimentoValidator.validarContaDestinoObrigatoria(dto);

        ContaResponseDTO contaOrigem = contaValidator.validarContaExistePorId(dto.getContaOrigemId());
        contaValidator.validarContaAtiva(contaOrigem);
        contaValidator.validarSaldoSuficiente(contaOrigem, dto.getValor());

        ContaResponseDTO contaDestino = contaValidator.validarContaExistePorId(dto.getContaDestinoId());
        contaValidator.validarContaAtiva(contaDestino);
        contaValidator.validarContasDiferentes(dto.getContaOrigemId(), dto.getContaDestinoId());

        MovimentoResponseDTO movimento = restTemplate.postForObject(
                movimentosServiceUrl + "/transferir", dto, MovimentoResponseDTO.class);

        atualizarSaldoConta(contaOrigem.getNumeroConta(), dto.getValor().negate());
        atualizarSaldoConta(contaDestino.getNumeroConta(), dto.getValor());
        return movimento;
    }

    private void atualizarSaldoConta(String numeroConta, BigDecimal valor) {
        ContaAtualizarSaldoRequestDTO saldoDTO = new ContaAtualizarSaldoRequestDTO();
        saldoDTO.setNumeroConta(numeroConta);
        saldoDTO.setValor(valor);

        restTemplate.exchange(
                contaServiceUrl + "/atualizarSaldo",
                HttpMethod.PATCH,
                new HttpEntity<>(saldoDTO),
                ContaResponseDTO.class
        );
    }

    @Override
    public List<MovimentoResponseDTO> buscarMovimentosPorNumeroConta(String numeroConta) {
        return movimentoValidator.buscarMovimentosPorNumeroConta(numeroConta);
    }

    @Override
    public List<MovimentoResponseDTO> buscarMovimentosPorClienteId(Long clienteId) {
        clienteValidator.validarClienteExiste(clienteId);
        return movimentoValidator.buscarMovimentosPorClienteId(clienteId);
    }

    // ─── CONTA + CLIENTE ─────────────────────────────────────────────────────

    @Override
    public ContaComClienteResponseDTO buscarContaComCliente(String numeroConta) {
        ContaResponseDTO conta = contaValidator.validarContaExistePorNumero(numeroConta);
        ClienteResponseDTO cliente = clienteValidator.validarClienteExiste(conta.getClienteId());

        ContaComClienteResponseDTO response = new ContaComClienteResponseDTO();
        response.setConta(conta);
        response.setCliente(cliente);
        return response;
    }

    @Override
    public List<ContaResponseDTO> listarContasDoCliente(Long clienteId) {
        clienteValidator.validarClienteExiste(clienteId);
        return contaValidator.buscarContasPorClienteId(clienteId);
    }

    // ─── EXTRATO ─────────────────────────────────────────────────────────────

    @Override
    public ExtratoContaResponseDTO buscarExtratoConta(String numeroConta) {
        ContaComClienteResponseDTO contaComCliente = buscarContaComCliente(numeroConta);
        List<MovimentoResponseDTO> movimentos = movimentoValidator.buscarMovimentosPorNumeroConta(numeroConta);

        ExtratoContaResponseDTO extrato = new ExtratoContaResponseDTO();
        extrato.setCliente(contaComCliente.getCliente());
        extrato.setConta(contaComCliente.getConta());
        extrato.setMovimentos(movimentos);
        return extrato;
    }

    @Override
    public ExtratoClienteResponseDTO buscarExtratoCliente(Long clienteId) {
        ClienteResponseDTO cliente = clienteValidator.validarClienteExiste(clienteId);
        List<ContaResponseDTO> contas = contaValidator.buscarContasPorClienteId(clienteId);

        List<ContaComMovimentosDTO> contasComMovimentos = contas.stream()
                .map(conta -> {
                    List<MovimentoResponseDTO> movimentos =
                            movimentoValidator.buscarMovimentosPorNumeroConta(conta.getNumeroConta());
                    ContaComMovimentosDTO item = new ContaComMovimentosDTO();
                    item.setConta(conta);
                    item.setMovimentos(movimentos);
                    return item;
                })
                .collect(Collectors.toList());

        ExtratoClienteResponseDTO extrato = new ExtratoClienteResponseDTO();
        extrato.setCliente(cliente);
        extrato.setContas(contasComMovimentos);
        return extrato;
    }

    // ─── FILTROS ─────────────────────────────────────────────────────────────

    @Override
    public List<MovimentoResponseDTO> buscarMovimentosPorTipo(String numeroConta, String tipo) {
        return movimentoValidator.buscarMovimentosPorNumeroConta(numeroConta).stream()
                .filter(m -> m.getTipoMovimento().toString().equals(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimentoResponseDTO> buscarUltimosMovimentos(String numeroConta, int quantidade) {
        return movimentoValidator.buscarMovimentosPorNumeroConta(numeroConta).stream()
                .sorted(Comparator.comparing(MovimentoResponseDTO::getDataMovimento).reversed())
                .limit(quantidade)
                .collect(Collectors.toList());
    }
}
