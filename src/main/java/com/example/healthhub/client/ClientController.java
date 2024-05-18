package com.example.healthhub.client;

import com.example.healthhub.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired

    private ClientService clientService;


    @GetMapping("/getall")
    public ArrayList<Client> getClients() {
        return this.clientService.getClients();
    }

    @GetMapping(path = "/{id}")
    public Optional<Client> getClientById(@PathVariable("id") Long id) {
        return this.clientService.getClientById(id);
    }

    @PostMapping("/save")
    public Client saveClient(@RequestBody Client client) {
        return this.clientService.saveClient(client);
    }


    @DeleteMapping(path = "/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        boolean ok = this.clientService.deleteClient(id);

        if(ok){
            return "User with Id" + id + "has been deleted";
        } else {
            return "Error, User with Id" + id + "has not been deleted";
        }
    }

    @PutMapping(path = "/{id}")
    public Client updateClient(@RequestBody Client request, @PathVariable("id") Long id) {
        return this.clientService.updateClient(request, id);
    }

}
