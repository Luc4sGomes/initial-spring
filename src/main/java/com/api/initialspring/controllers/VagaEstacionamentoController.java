package com.api.initialspring.controllers;

import com.api.initialspring.dtos.VagaEstacionamentoDTO;
import com.api.initialspring.models.VagaEstacionamentoModel;
import com.api.initialspring.services.VagaEstacionamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga-estacionamento")
public class VagaEstacionamentoController {
    final VagaEstacionamentoService vagaEstacionamentoService;

    public VagaEstacionamentoController(VagaEstacionamentoService vagaEstacionamentoService) {
        this.vagaEstacionamentoService = vagaEstacionamentoService;
    }

    @PostMapping("/criar-vaga")
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid VagaEstacionamentoDTO vagaEstacionamentoDTO) {

        if (vagaEstacionamentoService.existsByLicensePlateCar(vagaEstacionamentoDTO.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: License Plate car is Already in use!");
        }

        if (vagaEstacionamentoService.existsByParkingSpotNumber(vagaEstacionamentoDTO.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Parking Spot is Already in use!");
        }
        if (vagaEstacionamentoService.existsByApartmentAndBlock(vagaEstacionamentoDTO.getApartment(), vagaEstacionamentoDTO.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Parking Spot is Already register");
        }

        var vagaEstacionamentoModel = new VagaEstacionamentoModel();
        BeanUtils.copyProperties(vagaEstacionamentoDTO, vagaEstacionamentoModel);
        vagaEstacionamentoModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagaEstacionamentoService.save(vagaEstacionamentoModel));
    }

    @GetMapping("/obter-vagas")
    public ResponseEntity<Page<VagaEstacionamentoModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.findById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.findById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }
        vagaEstacionamentoService.delete(vagaEstacionamentoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id, @RequestBody @Valid VagaEstacionamentoDTO vagaEstacionamentoDTO) {
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.findById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }
        var vagaEstacionamentoModel = vagaEstacionamentoModelOptional.get();
        vagaEstacionamentoModel.setParkingSpotNumber(vagaEstacionamentoDTO.getParkingSpotNumber());
        vagaEstacionamentoModel.setLicensePlateCar(vagaEstacionamentoDTO.getLicensePlateCar());
        vagaEstacionamentoModel.setModelCar(vagaEstacionamentoDTO.getModelCar());
        vagaEstacionamentoModel.setBrandCar(vagaEstacionamentoDTO.getBrandCar());
        vagaEstacionamentoModel.setColorCar(vagaEstacionamentoDTO.getColorCar());
        vagaEstacionamentoModel.setResponsibleName(vagaEstacionamentoDTO.getResponsibleName());
        vagaEstacionamentoModel.setApartment(vagaEstacionamentoDTO.getApartment());
        vagaEstacionamentoModel.setBlock(vagaEstacionamentoDTO.getBlock());

        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.save(vagaEstacionamentoModel));
    }
}
