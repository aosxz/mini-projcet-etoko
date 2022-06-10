package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.shipment.ShipmentHeaderDTO;
import com.miniproject.etoko.dtos.shipment.UpsertShipmentDTO;
import com.miniproject.etoko.models.Shipment;
import com.miniproject.etoko.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImplementation implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<ShipmentHeaderDTO> findAllShipment(){
        List<Shipment> shipmentList = shipmentRepository.findAll();
        List<ShipmentHeaderDTO> shipmentStream =shipmentList.stream()
                .map(shipment -> new ShipmentHeaderDTO(
                        shipment.getNameShipment(),
                        shipment.getDescription(),
                        shipment.getCost()
                )).collect(Collectors.toList());
        return shipmentStream;
    }

    @Override
    public List<ShipmentHeaderDTO>findShipmentByName(String nameShipment){
        Optional<Shipment> shipmentList = Optional.ofNullable(shipmentRepository.findById(nameShipment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan")));
        List<ShipmentHeaderDTO> shipmentStream =shipmentList.stream()
                .map(shipment -> new ShipmentHeaderDTO(
                        shipment.getNameShipment(),
                        shipment.getDescription(),
                        shipment.getCost()
                )).collect(Collectors.toList());
        return shipmentStream;
    }


    @Override
    public List<ShipmentHeaderDTO>insertShipment(UpsertShipmentDTO newShipment){
        Shipment shipment = newShipment.convert();
        shipmentRepository.save(shipment);
        return ShipmentHeaderDTO.toList(shipmentRepository.findAll());
    }

    @Override
    public boolean updateShipment(String shipmentId, UpsertShipmentDTO upsertShipmentDTO){
        Shipment oldShipment = shipmentRepository.findById(shipmentId).orElseThrow(()-> new EntityNotFoundException(" Tidak dapat update karena Shipment "+shipmentId+ " tidak ada"));

        oldShipment.setDescription(upsertShipmentDTO.getDescription() == null ? oldShipment.getDescription() : upsertShipmentDTO.getDescription());
        oldShipment.setCost(upsertShipmentDTO.getCost() == null ? oldShipment.getCost() : upsertShipmentDTO.getCost());

        shipmentRepository.save(oldShipment);
        return true;
    }

    @Override
    public boolean deleteShipment(String shipmentId){
        shipmentRepository.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment dengan nama "+ shipmentId+" Tidak ditemukan di database"));
        shipmentRepository.deleteById(shipmentId);
        return true;
    }

}
