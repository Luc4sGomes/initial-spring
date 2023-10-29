package com.api.initialspring.services;

import com.api.initialspring.controllers.ParkingSpotController;
import com.api.initialspring.models.ParkingSpotModel;
import com.api.initialspring.repositories.ParkingSpotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotServiceTest {

    private ParkingSpotService parkingSpotService;

    private ParkingSpotRepository parkingSpotRepository;

    @BeforeEach
    void setUp() {
        parkingSpotRepository = Mockito.mock(ParkingSpotRepository.class);
        parkingSpotService = new ParkingSpotService(parkingSpotRepository);
    }

    @Test
    void testSaveParkingSpot() {
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        Mockito.when(parkingSpotRepository.save(parkingSpotModel)).thenReturn(parkingSpotModel);

        ParkingSpotModel savedSpot = parkingSpotService.save(parkingSpotModel);

        Assertions.assertEquals(parkingSpotModel, savedSpot);
    }

    @Test
    void testExistsByLicensePlateCar() {
        String licensePlateCar = "ABC123";
        Mockito.when(parkingSpotRepository.existsByLicensePlateCar(licensePlateCar)).thenReturn(true);

        boolean exists = parkingSpotService.existsByLicensePlateCar(licensePlateCar);

        Assertions.assertTrue(exists);
    }

    @Test
    void testExistsByParkingSpotNumber() {
        String parkingSpotNumber = "12345";
        Mockito.when(parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber)).thenReturn(true);

        boolean exists = parkingSpotService.existsByParkingSpotNumber(parkingSpotNumber);

        Assertions.assertTrue(exists);
    }

    @Test
    void testExistsByApartmentAndBlock() {
        String apartment = "A101";
        String block = "A";
        Mockito.when(parkingSpotRepository.existsByApartmentAndBlock(apartment, block)).thenReturn(true);

        boolean exists = parkingSpotService.existsByApartmentAndBlock(apartment, block);

        Assertions.assertTrue(exists);
    }


    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        Mockito.when(parkingSpotRepository.findById(id)).thenReturn(Optional.of(parkingSpotModel));

        Optional<ParkingSpotModel> result = parkingSpotService.findById(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(parkingSpotModel, result.get());
    }

    @Test
    void testDelete() {
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        parkingSpotService.delete(parkingSpotModel);

        Mockito.verify(parkingSpotRepository).delete(parkingSpotModel);
    }

    @Test
    void testGetAllParkingSpots() {
        List<ParkingSpotModel> parkingSpots = Mockito.mock(List.class);
        Mockito.when(parkingSpotRepository.findAll()).thenReturn(parkingSpots);

        List<ParkingSpotModel> result = parkingSpotService.getAllParkingSpots();

        Assertions.assertEquals(parkingSpots, result);
    }


}