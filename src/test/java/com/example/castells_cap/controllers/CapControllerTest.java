package com.example.castells_cap.controllers;

import com.example.castells_cap.models.Cap;
import com.example.castells_cap.repositories.CapRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CapControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    CapRepository capRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private Cap cap;

    @BeforeEach
    public void SetUp(){
        cap = new Cap();
        cap.setName("Test with MockMVC");
        cap.setEmail("test@testing.eu");
        System.out.println("USUARIO DEL TEST: " +cap);
        capRepository.save(cap);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @AfterEach
    public void tearDown(){
        Cap cap = capRepository.findCapByName("Test with MockMVC");
        if (cap!=null){
            capRepository.delete(cap);
        }

    }

    @Test
    public void createCap() throws Exception {
        String capJSON = objectMapper.writeValueAsString(cap);

        MvcResult result = mockMvc.perform(post("/api/cap")
                .contentType(MediaType.APPLICATION_JSON)
                .content(capJSON)
        ).andExpect(status().isCreated()).andReturn();
        String stringResponse = result.getResponse().getContentAsString();
        assertTrue(stringResponse.contains("Test with MockMVC"));

    }

    @Test
    public void updateCap() throws Exception{
        String capJSON = objectMapper.writeValueAsString(cap);

        MvcResult result = mockMvc.perform(put("/api/cap/"+cap.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(capJSON)
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Test with MockMVC"));
    }

    @Test
    public void deleteCap() throws Exception {
        String capJSON = objectMapper.writeValueAsString(cap);

        MvcResult result = mockMvc.perform(delete("/api/cap/"+cap.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(capJSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertFalse(result.getResponse().getContentAsString().contains("Test with MockMVC"));

    }
}
