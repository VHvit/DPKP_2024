package com.example.controllers;

import com.example.model.dto.ClientDto;
import com.example.model.search.SearchClientDto;
import com.example.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClient() {
        List<ClientDto> clients = clientService.getAllClient();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        return clientService.createClient(clientDto);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable("clientId") UUID clientId,
            @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = clientService.updateClient(clientId, clientDto);
        return updatedClient != null ? ResponseEntity.ok(updatedClient) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") UUID clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long clientCount() {
        return clientService.clientCount();
    }

    @GetMapping("/count/{clientId}")
    public int getTotalRecordCount(@PathVariable UUID clientId) {
        return clientService.getTotalRecordCount(clientId);
    }

    @GetMapping("/search")
    public SearchClientDto getClient(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String middlename,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date birthday) {
        return clientService.getClients(firstname, lastname, middlename, birthday);
    }

}