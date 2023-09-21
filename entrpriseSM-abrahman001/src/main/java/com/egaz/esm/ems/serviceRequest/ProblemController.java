package com.egaz.esm.ems.serviceRequest;

import com.egaz.esm.ems.serviceRequest.services.RequestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("api/v2/request")
public class ProblemController {

    @Autowired
    private RequestServices requestServices;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody Srequest n){
        Srequest s = requestServices.addRequest(n);
        return ResponseEntity.ok(s);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllClient(){
        List<Srequest> list = requestServices.getAllRequest();
        return ResponseEntity.ok(list);
    }
    @PutMapping("/update")
    public Srequest updateRequest(@RequestBody Srequest s,@RequestParam Long serviceID){
        return requestServices.updateRequest(s,serviceID);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long serviceID){
        Optional<Srequest> s = requestServices.findById(serviceID);
        return ResponseEntity.ok(s);
    }

}
