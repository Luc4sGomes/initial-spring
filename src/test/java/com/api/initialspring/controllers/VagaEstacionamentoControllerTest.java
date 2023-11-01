package com.api.initialspring.controllers;

import com.api.initialspring.models.VagaEstacionamentoModel;
import com.api.initialspring.services.VagaEstacionamentoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(VagaEstacionamentoController.class)
class VagaEstacionamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VagaEstacionamentoService vagaEstacionamentoService;


    @Test
    void testSaveParkingSpotSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/vaga-estacionamento/criar-vaga")
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"ABC123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    void getAllParkingSpots() throws Exception {
        VagaEstacionamentoModel vagaEstacionamento = new VagaEstacionamentoModel();
        vagaEstacionamento.setId(UUID.randomUUID());
        vagaEstacionamento.setParkingSpotNumber("123");

        Page<VagaEstacionamentoModel> vagaEstacionamentoPage = new PageImpl<>(Collections.singletonList(vagaEstacionamento));

        when(vagaEstacionamentoService.findAll(Mockito.any())).thenReturn(vagaEstacionamentoPage);

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento/obter-vagas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].parkingSpotNumber").value("123"));
    }

    @Test
    void testGetOneParkingSpotFound() throws Exception {
        VagaEstacionamentoModel vagaEstacionamento = new VagaEstacionamentoModel();
        vagaEstacionamento.setId(UUID.randomUUID());

        when(vagaEstacionamentoService.findById(vagaEstacionamento.getId())).thenReturn(Optional.of(vagaEstacionamento));

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento/{id}", vagaEstacionamento.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vagaEstacionamento.getId().toString()));
    }

    @Test
    void testGetOneParkingSpotNotFound() throws Exception {
        when(vagaEstacionamentoService.findById(Mockito.any())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento/{id}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }


    @Test
    void testDeleteParkingSpotFound() throws Exception {
        VagaEstacionamentoModel vagaEstacionamento = new VagaEstacionamentoModel();
        UUID vagaEstacionamentoId = UUID.randomUUID();
        vagaEstacionamento.setId(vagaEstacionamentoId);

        when(vagaEstacionamentoService.findById(vagaEstacionamentoId)).thenReturn(Optional.of(vagaEstacionamento));

        mvc.perform(MockMvcRequestBuilders.delete("/vaga-estacionamento/{id}", vagaEstacionamentoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vagaEstacionamentoId.toString()));
    }

    @Test
    void testDeleteParkingSpotNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        when(vagaEstacionamentoService.findById(nonExistentId)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.delete("/vaga-estacionamento/{id}", nonExistentId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }

    @Test
    void testUpdateParkingSpotFound() throws Exception {
        VagaEstacionamentoModel vagaEstacionamento = new VagaEstacionamentoModel();
        UUID vagaEstacionamentoId = UUID.randomUUID();
        vagaEstacionamento.setId(vagaEstacionamentoId);

        when(vagaEstacionamentoService.findById(vagaEstacionamentoId)).thenReturn(Optional.of(vagaEstacionamento));

        mvc.perform(MockMvcRequestBuilders.put("/vaga-estacionamento/{id}", vagaEstacionamentoId)
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"XYZ123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateParkingSpotNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        when(vagaEstacionamentoService.findById(nonExistentId)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/vaga-estacionamento/{id}", nonExistentId)
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"XYZ123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }
}