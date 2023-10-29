package com.api.initialspring.controllers;

import com.api.initialspring.models.ParkingSpotModel;
import com.api.initialspring.services.ParkingSpotService;
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

@WebMvcTest(ParkingSpotController.class)
class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingSpotService parkingSpotService;


    @Test
    void testSaveParkingSpotSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/vaga-estacionamento")
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"ABC123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    void getAllParkingSpots() throws Exception {
        ParkingSpotModel parkingSpot = new ParkingSpotModel();
        parkingSpot.setId(UUID.randomUUID());
        parkingSpot.setParkingSpotNumber("123");

        Page<ParkingSpotModel> parkingSpotPage = new PageImpl<>(Collections.singletonList(parkingSpot));

        when(parkingSpotService.findAll(Mockito.any())).thenReturn(parkingSpotPage);

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].parkingSpotNumber").value("123"));
    }

    @Test
    void testGetOneParkingSpotFound() throws Exception {
        ParkingSpotModel parkingSpot = new ParkingSpotModel();
        parkingSpot.setId(UUID.randomUUID());

        when(parkingSpotService.findById(parkingSpot.getId())).thenReturn(Optional.of(parkingSpot));

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento/{id}", parkingSpot.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(parkingSpot.getId().toString()));
    }

    @Test
    void testGetOneParkingSpotNotFound() throws Exception {
        when(parkingSpotService.findById(Mockito.any())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/vaga-estacionamento/{id}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }


    @Test
    void testDeleteParkingSpotFound() throws Exception {
        ParkingSpotModel parkingSpot = new ParkingSpotModel();
        UUID parkingSpotId = UUID.randomUUID();
        parkingSpot.setId(parkingSpotId);

        when(parkingSpotService.findById(parkingSpotId)).thenReturn(Optional.of(parkingSpot));

        mvc.perform(MockMvcRequestBuilders.delete("/vaga-estacionamento/{id}", parkingSpotId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(parkingSpotId.toString()));
    }

    @Test
    void testDeleteParkingSpotNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        when(parkingSpotService.findById(nonExistentId)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.delete("/vaga-estacionamento/{id}", nonExistentId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }

    @Test
    void testUpdateParkingSpotFound() throws Exception {
        ParkingSpotModel parkingSpot = new ParkingSpotModel();
        UUID parkingSpotId = UUID.randomUUID();
        parkingSpot.setId(parkingSpotId);

        when(parkingSpotService.findById(parkingSpotId)).thenReturn(Optional.of(parkingSpot));

        mvc.perform(MockMvcRequestBuilders.put("/vaga-estacionamento/{id}", parkingSpotId)
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"XYZ123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateParkingSpotNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        when(parkingSpotService.findById(nonExistentId)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/vaga-estacionamento/{id}", nonExistentId)
                        .contentType("application/json")
                        .content("{\"parkingSpotNumber\":\"1234\",\"licensePlateCar\":\"XYZ123\",\"brandCar\":\"Ford\",\"modelCar\":\"Focus\",\"colorCar\":\"Blue\",\"responsibleName\":\"John Doe\",\"apartment\":\"A101\",\"block\":\"A\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Parking spot not found"));
    }
}