package com.william.banco.cliente_service.SERVICE;

import com.william.banco.cliente_service.DTO.ClienteRequestDTO;
import com.william.banco.cliente_service.DTO.ClienteResponseDTO;

import java.util.List;


public interface IClienteService {
    ClienteResponseDTO criarCliente(ClienteRequestDTO clienteRequestDTO);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO buscarPorID(Long id);
    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto);
    void deletar(Long id);
}
