package com.egaz.esm.ems.clients;

import com.egaz.esm.ems.clients.dto.ClientRequest;
import com.egaz.esm.ems.clients.dto.ClientResponse;
import com.egaz.esm.ems.clients.services.ClientsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("api/v1/client")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<?>  addUser(@RequestBody ClientRequest n){
        Client cr= clientsService.addClient(n);
        ClientResponse client = modelMapper.map(cr,ClientResponse.class);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllClient(){
        List<Client> list = clientsService.getAllClient();
        List<ClientResponse> resp= list.stream().map(x->modelMapper.map(x,ClientResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/update")
    public Client updateBooking(@RequestBody Client client,@RequestParam Long clientID){
        return clientsService.updateClient(client,clientID);
    }



}
