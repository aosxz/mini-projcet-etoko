package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.shipment.ShipmentHeaderDTO;
import com.miniproject.etoko.dtos.shipment.UpsertShipmentDTO;

import java.util.List;

public interface ShipmentService {
    public List<ShipmentHeaderDTO> findAllShipment();

    List<ShipmentHeaderDTO>findShipmentByName(String nameShipment);

    public List<ShipmentHeaderDTO>insertShipment(UpsertShipmentDTO newShipment);

    public boolean updateShipment(String shipmentId, UpsertShipmentDTO upsertShipmentDTO);

    public boolean deleteShipment(String shipmentId);
}
