package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clients= clientRepository.findAll();
        return new ResponseEntity<>(clients.stream().map(ClientDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ClientDTO clientDTO = new ClientDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("hello" + email);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        return ResponseEntity.ok(new ClientDTO(client));
    }
}
