package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.enums.Profile;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.dto.ClientDTO;
import com.marcoscouto.cursomc.dto.ClientInsertDTO;
import com.marcoscouto.cursomc.repositories.AddressRepository;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.security.UserSS;
import com.marcoscouto.cursomc.services.exceptions.AuthorizationException;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder bcp;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(Integer id){

        UserSS userSS = UserService.authenticated();

        if(userSS == null || !userSS.hasRole(Profile.ADMIN) && !id.equals(userSS.getId()))
            throw new AuthorizationException("Access Denied");


        Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id +
                ", Type: " + Client.class.getName()));
    }

    @Transactional
    public Client save(Client client){
        client.setId(null);
        client.getAddresses().forEach(
                x -> {
                    System.out.println(x);
                    addressRepository.save(x);
                }
        );
        client = clientRepository.save(client);
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
                TypeClient.toEnum(clientInsertDTO.getTypeClient()),
                bcp.encode(clientInsertDTO.getPassword())
                );


        clientInsertDTO.getAddresses().forEach(x -> {
            x.setClient(client);
        });
        client.getAddresses().addAll(
                clientInsertDTO.getAddresses()
        );

        client.getPhones().addAll(clientInsertDTO.getPhones());
        return client;
    }

    private void updateData(Client client, Client obj) {
        if(obj.getName() != null) client.setName(obj.getName());
        if(obj.getEmail() != null) client.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        return s3Service.uploadFile(multipartFile);
    }
}
