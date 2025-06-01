package com.example.castells_cap.services;

import com.example.castells_cap.DTOs.CapDTO;
import com.example.castells_cap.models.Cap;
import com.example.castells_cap.repositories.CapRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CapServicesTest {
    @Autowired
    CapService capService;
    @Autowired
    CapRepository capRepository;

    private Cap cap;

    @BeforeEach
    public void setUp(){
        cap = new Cap();
        cap.setName("Test Testing");
        cap.setEmail("test@testing.eu");
        System.out.println("CAP DEL TEST: " +cap);
        capService.createCap(cap);
    }
    @AfterEach
    public void tearDown(){
        capRepository.delete(cap);
    }

    @Test
    @DisplayName("Crear Cap")
    public void createCap(){
        assertNotNull(cap.getId(), "El Cap deberia ser null por que no pasa por BBDD");
        assertEquals("Test Testing", cap.getName(), "Nombre correcto");
        assertEquals("test@testing.eu", cap.getEmail(), "Email Correcto");


    }

    @Test
    @DisplayName("Buscar Cap por ID")
    public void findById(){
        Cap foundCap = capService.findCapById(cap.getId());
        assertNotNull(foundCap);
        assertEquals(cap.getName(), foundCap.getName());
        assertEquals(cap.getEmail(), foundCap.getEmail());
        System.out.println("USUARIO A COMPARAR: " +foundCap);
    }

    @Test
    @DisplayName("Actualizar email de Cap")
    public void updateCapEmail(){
        CapDTO updateCap = new CapDTO();
        updateCap.setEmail("nuevo@nuevo.es");
        Cap updatedCap = capService.updateCapEmail(cap.getId(), updateCap);
        assertNotNull(updatedCap);
        assertEquals("nuevo@nuevo.es", updatedCap.getEmail());
        System.out.println("USUARIO A COMPARAR: " +updatedCap);
    }
    @Test
    @DisplayName("Eliminar Cap")
    public void deleteCap(){
        capService.deleteCap(cap.getId());
        assertFalse(capRepository.existsById(cap.getId()), "Deberia estar eliminado de la BBDD");
        System.out.println("USUARIO A COMPARAR: " + cap);
    }
}
