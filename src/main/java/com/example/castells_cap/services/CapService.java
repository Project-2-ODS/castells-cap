package com.example.castells_cap.services;

import com.example.castells_cap.DTOs.CapDTO;
import com.example.castells_cap.exceptions.CapNotFoundException;
import com.example.castells_cap.models.Cap;
import com.example.castells_cap.repositories.CapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.CardException;
import java.util.Optional;

@Service
public class CapService {
    @Autowired
    CapRepository capRepository;

    //CREATE
    public Cap createCap(Cap cap){
        return capRepository.save(cap);
    }

    //READ
    public Cap findCapById(Long id){
        Optional<Cap> foundCap = capRepository.findById(id);
        if(foundCap.isPresent()){
            return foundCap.get();
        }else{
            throw new CapNotFoundException("Cap no encontrado.");
        }
    }
    //UPDATE ONLY EMAIL
    public Cap updateCapEmail(Long id, CapDTO capDTO){
        Cap existingCap = capRepository.findById(id).orElseThrow(()-> new CapNotFoundException("Cap con id " +id+" no encontrado."));
        if (capDTO.getEmail()!=null){
            existingCap.setEmail(capDTO.getEmail());
        }
        return capRepository.save(existingCap);
    }
    //UPDATE CAP ALL INFO
    public Cap updateCapAllInfo(Long id, Cap cap){
        Cap existingCap = capRepository.findById(id).orElseThrow(()-> new CapNotFoundException("Cap con id " +id+" no encontrado."));
        existingCap.setName(cap.getName());
        existingCap.setEmail(cap.getEmail());
        return capRepository.save(existingCap);

    }

    //DELETE
    public Cap deleteCap(Long id){
        Cap existingCap = capRepository.findById(id).orElseThrow(()-> new CapNotFoundException("Cap con id " +id+" no encontrado."));
        capRepository.delete(existingCap);
        return existingCap;
    }
}
