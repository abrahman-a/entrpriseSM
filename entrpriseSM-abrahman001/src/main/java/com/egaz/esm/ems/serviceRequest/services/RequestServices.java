package com.egaz.esm.ems.serviceRequest.services;


import com.egaz.esm.ems.serviceRequest.Srequest;
import com.egaz.esm.ems.serviceRequest.repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServices {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Srequest addRequest(Srequest request){
        Srequest s = modelMapper.map(request,Srequest.class);
        return requestRepository.save(s);
    }
    public List<Srequest> getAllRequest(){
        return requestRepository.findAll();
    }

    public Optional<Srequest> findById(Long id){
        return requestRepository.findById(id);
    }
    public Srequest updateRequest(Srequest request,Long serviceID){
        request.setServiceID(serviceID);
        return requestRepository.save(request);
    }




}
