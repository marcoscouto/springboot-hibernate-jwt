package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Address;
import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.domain.City;
import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.dto.ClientDTO;
import com.marcoscouto.cursomc.dto.ClientInsertDTO;
import com.marcoscouto.cursomc.repositories.AddressRepository;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(Integer id){
        Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id +
                ", Type: " + Client.class.getName()));
    }

    @Transactional
    public Client save(Client client){
        client.setId(null);
        client = clientRepository.save(client);
        addressRepository.save(client.getAddresses().get(0));
        return client;
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

    public Client fromDTO(ClientInsertDTO clientInsertDTO){
        Client client = new Client(
                null,
                clientInsertDTO.getName(),
                clientInsertDTO.getEmail(),
                clientInsertDTO.getDocument(),
                TypeClient.toEnum(clientInsertDTO.getTypeClient())
                );
        Address address = new Address(
                null,
                clientInsertDTO.getStreet(),
                clientInsertDTO.getNumber(),
                clientInsertDTO.getComplement(),
                clientInsertDTO.getNeighborhood(),
                clientInsertDTO.getZipCode(),
                client,
                new City(clientInsertDTO.getCityId(), null, null)
        );
        client.getAddresses().add(address);
        client.getPhones().add(clientInsertDTO.getPhone1());
        if(clientInsertDTO.getPhone2() != null ) client.getPhones().add(clientInsertDTO.getPhone2());
        if(clientInsertDTO.getPhone3() != null ) client.getPhones().add(clientInsertDTO.getPhone3());
        return client;
    }

    private void updateData(Client client, Client obj) {
        if(obj.getName() != null) client.setName(obj.getName());
        if(obj.getEmail() != null) client.setEmail(obj.getEmail());
    }
}
