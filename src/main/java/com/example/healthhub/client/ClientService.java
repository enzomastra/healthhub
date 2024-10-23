package com.example.healthhub.client;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ClientService {
    
    @Autowired
    ClientRepository clientRepository;

    
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ArrayList<Client> getClients() {
        return (ArrayList<Client>) clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Boolean deleteClient(Long id) {
        try {
            clientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Client updateClient(Client request, Long id) {
        Optional<Client> client = clientRepository.findById(id);
        
        if (client.isPresent()) {
            Client clientToSave = client.get();
            if (request.getName() != null) {
                clientToSave.setName(request.getName());
            }
            if (request.getEmail() != null) {
                clientToSave.setEmail(request.getEmail());
            }
            if (request.getWeight() != null) {
                clientToSave.setWeight(request.getWeight());
            }
            if (request.getHeight() != null) {
                clientToSave.setHeight(request.getHeight());
            }
            return clientRepository.save(clientToSave);
        } else {
            return null;
        }
    }
}