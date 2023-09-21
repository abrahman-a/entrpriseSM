package com.egaz.esm.ems.clients;

import com.egaz.esm.ems.apis.EmployeeInfoApi;
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

    @Autowired
    private EmployeeInfoApi employeeInfoApi;

    @PostMapping("/add")
    public ResponseEntity<?>  addClient(@RequestBody ClientRequest n){
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

//    @PutMapping("/update")
//    public Client updateBooking(@RequestBody Client client,@RequestParam Long clientID){
//        return clientsService.updateClient(client,clientID);
//    }

    @PostMapping("/updatePassword/{clientID}")
    public ResponseEntity<Client> updatePassword(
            @PathVariable("clientID") Long clientID,
            @RequestBody Client updatedPassword) {
        Client updated = clientsService.updatePassword(clientID, updatedPassword);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{email}")
    public Client getByEmail(@PathVariable("email") String email){
        return  clientsService.getClient(email);
    }
    @GetMapping("/emp-inf")
    public ResponseEntity<?> getEmpInf(){

        return  ResponseEntity.ok(employeeInfoApi.employeeInfo(10001l).getBody());
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable("email") String email){
        clientsService.delete(email);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client){
        return ResponseEntity.ok(clientsService.update(client));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Client> updateUser(
            @PathVariable("id") Long clientID,
            @RequestBody Client updatedClient) {
        Client updated = clientsService.updateClient(clientID, updatedClient);
        return ResponseEntity.ok(updated);
    }

}
