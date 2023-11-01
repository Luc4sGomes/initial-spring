package com.api.initialspring.services;

import com.api.initialspring.models.VagaEstacionamentoModel;
import com.api.initialspring.repositories.VagaEstacionamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class VagaEstacionamentoServiceTest {

    private VagaEstacionamentoService vagaEstacionamentoService;

    private VagaEstacionamentoRepository vagaEstacionamentoRepository;

    @BeforeEach
    void setUp() {
        vagaEstacionamentoRepository = Mockito.mock(VagaEstacionamentoRepository.class);
        vagaEstacionamentoService = new VagaEstacionamentoService(vagaEstacionamentoRepository);
    }

    @Test
    void testSaveParkingSpot() {
        VagaEstacionamentoModel vagaEstacionamentoModel = new VagaEstacionamentoModel();
        Mockito.when(vagaEstacionamentoRepository.save(vagaEstacionamentoModel)).thenReturn(vagaEstacionamentoModel);

        VagaEstacionamentoModel savedSpot = vagaEstacionamentoService.save(vagaEstacionamentoModel);

        Assertions.assertEquals(vagaEstacionamentoModel, savedSpot);
    }

    @Test
    void testExistsByLicensePlateCar() {
        String licensePlateCar = "ABC123";
        Mockito.when(vagaEstacionamentoRepository.existsByLicensePlateCar(licensePlateCar)).thenReturn(true);

        boolean exists = vagaEstacionamentoService.existsByLicensePlateCar(licensePlateCar);

        Assertions.assertTrue(exists);
    }

    @Test
    void testExistsByParkingSpotNumber() {
        String parkingSpotNumber = "12345";
        Mockito.when(vagaEstacionamentoRepository.existsByParkingSpotNumber(parkingSpotNumber)).thenReturn(true);

        boolean exists = vagaEstacionamentoService.existsByParkingSpotNumber(parkingSpotNumber);

        Assertions.assertTrue(exists);
    }

    @Test
    void testExistsByApartmentAndBlock() {
        String apartment = "A101";
        String block = "A";
        Mockito.when(vagaEstacionamentoRepository.existsByApartmentAndBlock(apartment, block)).thenReturn(true);

        boolean exists = vagaEstacionamentoService.existsByApartmentAndBlock(apartment, block);

        Assertions.assertTrue(exists);
    }


    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        VagaEstacionamentoModel vagaEstacionamentoModel = new VagaEstacionamentoModel();
        Mockito.when(vagaEstacionamentoRepository.findById(id)).thenReturn(Optional.of(vagaEstacionamentoModel));

        Optional<VagaEstacionamentoModel> result = vagaEstacionamentoService.findById(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(vagaEstacionamentoModel, result.get());
    }

    @Test
    void testDelete() {
        VagaEstacionamentoModel vagaEstacionamentoModel = new VagaEstacionamentoModel();
        vagaEstacionamentoService.delete(vagaEstacionamentoModel);

        Mockito.verify(vagaEstacionamentoRepository).delete(vagaEstacionamentoModel);
    }

    @Test
    void testGetAllParkingSpots() {
        List<VagaEstacionamentoModel> parkingSpots = Mockito.mock(List.class);
        Mockito.when(vagaEstacionamentoRepository.findAll()).thenReturn(parkingSpots);

        List<VagaEstacionamentoModel> result = vagaEstacionamentoService.getAllParkingSpots();

        Assertions.assertEquals(parkingSpots, result);
    }


}