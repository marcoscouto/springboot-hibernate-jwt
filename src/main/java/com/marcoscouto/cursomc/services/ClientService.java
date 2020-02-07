package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.dto.ClientDTO;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    public Client update(Integer id, Client obj){
        Client client = findById(id);
        updateData(client, obj);
        return clientRepository.save(client);
    }

    public void delete(Integer id){
        findById(id);
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("It's not possible delete a client with orders.");
        }
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clientRepository.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO clientDTO){
        Client client = new Client(
                clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getEmail(),
                null,
                null
                );
        return client;
    }

    public Client save(Client client){
        return new Client();
    }


    private void updateData(Client client, Client obj) {
        if(obj.getName() != null) client.setName(obj.getName());
        if(obj.getEmail() != null) client.setEmail(obj.getEmail());
    }
}
