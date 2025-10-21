package com.retailx.client.service;
import com.retailx.client.domain.Client;

import com.retailx.client.dto.ClientDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClientService {

    public List<ClientDTO> listAll() {
        return Client.listAll().stream()
                .map(c -> {
                    Client client = (Client) c;
                    return new ClientDTO(
                            client.getId(),
                            client.getName(),
                            client.getEmail(),
                            client.getDocument()
                    );
                })
                .collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) {
        Client c = Client.findById(id);
        if (c == null) return null;
        return new ClientDTO(
                c.getId(),
                c.getName(),
                c.getEmail(),
                c.getDocument()
        );
    }

    @Transactional
    public ClientDTO create(ClientDTO dto) {
        Client c = new Client(dto.getName(), dto.getEmail(), dto.getDocument());
        c.persist();
        return new ClientDTO(c.getId(), c.getName(), c.getEmail(), c.getDocument());
    }

    @Transactional
    public boolean delete(Long id) {
        Client client = Client.findById(id);
        if (client == null) return false;
        client.delete();
        return true;
    }
}
