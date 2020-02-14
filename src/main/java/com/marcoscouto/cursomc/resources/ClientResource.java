package com.marcoscouto.cursomc.resources;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.dto.ClientDTO;
import com.marcoscouto.cursomc.dto.ClientInsertDTO;
import com.marcoscouto.cursomc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> clients = new ArrayList<>();
        clientService.findAll().forEach(
                x -> clients.add(new ClientDTO(x))
        );
        return ResponseEntity.ok().body(clients);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<ClientDTO> clients = clientService.findPage(page, linesPerPage, orderBy, direction).map(
                x -> new ClientDTO(x)
        );
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Integer id){
        Client client = clientService.findById(id);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<Client> insert(@Valid @RequestBody ClientInsertDTO clientInsertDTO){
        Client client = clientService.fromDTO(clientInsertDTO);
        client = clientService.save(client);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Integer id){
        Client client = clientService.fromDTO(clientDTO);
        client.setId(id);
        client = clientService.update(id, client);
        return ResponseEntity.ok().body(client);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
