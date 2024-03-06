package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    List<ClientDTO> getAllClientsDTO();

    Client getClientById(Long id);

    Client getClientByEmail(String email);

    void saveClient(Client client);


}
