package com.miniproject.etoko.controllers;


import com.miniproject.etoko.dtos.RestResponse;
import com.miniproject.etoko.dtos.buyer.BuyerHeaderDTO;
import com.miniproject.etoko.dtos.shipment.ShipmentHeaderDTO;
import com.miniproject.etoko.dtos.shipment.UpsertShipmentDTO;
import com.miniproject.etoko.services.ShipmentService;
import com.miniproject.etoko.services.ShipmentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;


    @GetMapping("getAll")
    public ResponseEntity<RestResponse<List<ShipmentHeaderDTO>>> getAllShipment(){
        return new ResponseEntity<>(
                new RestResponse<>(shipmentService.findAllShipment(),
                        "Shipment berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("find-by-name/{nameShipment}")
    public ResponseEntity<RestResponse<List<ShipmentHeaderDTO>>> findByNameShipment(@PathVariable String nameShipment){
        return new ResponseEntity<>(
                new RestResponse<>(shipmentService.findShipmentByName(nameShipment),
                        "Shipment dengan nama " + nameShipment + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<RestResponse<BuyerHeaderDTO>> insertShipment(@RequestBody UpsertShipmentDTO newShipment){
        return new ResponseEntity(
                new RestResponse<>(shipmentService.insertShipment(newShipment),
                        "Shipment berhasil ditambahkan",
                        201),
                HttpStatus.CREATED);
    }

    @PutMapping("update/{shipmentId}")
    public ResponseEntity<RestResponse> updateShipment(@PathVariable String shipmentId,@RequestBody UpsertShipmentDTO shipmentUpdateDTO){
        return new ResponseEntity(
                new RestResponse(shipmentService.updateShipment(shipmentId, shipmentUpdateDTO),
                        "Shipment ID " + shipmentId + " berhasil di update ",
                        201),
                HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{shipmentId}")
    public ResponseEntity<RestResponse> deleteShipment(@PathVariable String shipmentId){
        return new ResponseEntity(
                new RestResponse(shipmentService.deleteShipment(shipmentId),
                        "Shipment Name " + shipmentId + " berhasil dihapus ",
                        201),
                HttpStatus.OK);
    }



}
