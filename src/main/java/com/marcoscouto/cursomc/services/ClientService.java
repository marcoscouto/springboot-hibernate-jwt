package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(Integer id){
        Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id +
                ", Type: " + Client.class.getName()));
    }
}
