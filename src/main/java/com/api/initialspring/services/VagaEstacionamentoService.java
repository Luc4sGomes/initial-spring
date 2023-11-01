package com.api.initialspring.services;

import com.api.initialspring.models.VagaEstacionamentoModel;
import com.api.initialspring.repositories.VagaEstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VagaEstacionamentoService {

    final VagaEstacionamentoRepository vagaEstacionamentoRepository;

    public VagaEstacionamentoService(VagaEstacionamentoRepository vagaEstacionamentoRepository) {
        this.vagaEstacionamentoRepository = vagaEstacionamentoRepository;
    }

    @Transactional
    public VagaEstacionamentoModel save(VagaEstacionamentoModel vagaEstacionamentoModel) {
        return vagaEstacionamentoRepository.save(vagaEstacionamentoModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return vagaEstacionamentoRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return vagaEstacionamentoRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return vagaEstacionamentoRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<VagaEstacionamentoModel> findAll(Pageable pageable) {
        return vagaEstacionamentoRepository.findAll(pageable);
    }

    public Optional<VagaEstacionamentoModel> findById(UUID id) {
        return vagaEstacionamentoRepository.findById(id);
    }

    public void delete(VagaEstacionamentoModel vagaEstacionamentoModel) {
         vagaEstacionamentoRepository.delete(vagaEstacionamentoModel);
    }

    public List<VagaEstacionamentoModel> getAllParkingSpots() {
        return vagaEstacionamentoRepository.findAll();
    }
}
