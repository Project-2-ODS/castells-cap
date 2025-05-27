package com.example.castells_cap.controllers;

import com.example.castells_cap.DTOs.CapDTO;
import com.example.castells_cap.exceptions.CapNotFoundException;
import com.example.castells_cap.models.Cap;
import com.example.castells_cap.services.CapService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cap")
public class CapController {
    @Autowired
    CapService capService;

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cap createCap(@RequestBody @Valid Cap cap){
        return capService.createCap(cap);
    }

    //READ
    @GetMapping("/{id}")
    public ResponseEntity<?> getCapById(@PathVariable Long id){
        try {
            Cap foundCap = capService.findCapById(id);
            return new ResponseEntity<>(foundCap, HttpStatus.OK);
        }catch (CapNotFoundException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    //UPDATE ONLY EMAIL
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCapEmail(@PathVariable Long id, @RequestBody CapDTO capDTO){
        try {
            Cap existingCap = capService.updateCapEmail(id, capDTO);
            return ResponseEntity.ok(existingCap);
        }catch (CapNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
    //UPDATE ALL INFO
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCapAllInfo(@PathVariable Long id, @RequestBody Cap cap){
        try {
            Cap existingCap = capService.updateCapAllInfo(id, cap);
            return ResponseEntity.ok(existingCap);
        }catch (CapNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCap(@PathVariable Long id){
        try {
            Cap existingCap = capService.deleteCap(id);
            return ResponseEntity.noContent().build();
        }catch (CapNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
}
