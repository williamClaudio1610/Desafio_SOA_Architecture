package com.william.banco.cliente_service.SERVICE;

import com.william.banco.cliente_service.DTO.ClienteRequestDTO;
import com.william.banco.cliente_service.DTO.ClienteResponseDTO;
import com.william.banco.cliente_service.ENTITY.Cliente;
import com.william.banco.cliente_service.REPOSITORY.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService  implements IClienteService{

    @Autowired
    private ClienteRepository _clienterepository;


    @Override
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setName(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        Cliente salvo = _clienterepository.save(cliente);
        return toResponseDTO(salvo);
    }


    @Override
    public List<ClienteResponseDTO> listarTodos() {
        return _clienterepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO buscarPorID(Long id) {
        Cliente cliente = _clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return toResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = _clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setName(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        Cliente salvo = _clienterepository.save(cliente);
        return toResponseDTO(salvo);
    }

    @Override
    public void deletar(Long id) {
        Cliente cliente = _clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        _clienterepository.delete(cliente);

    }



    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getName());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }
}
