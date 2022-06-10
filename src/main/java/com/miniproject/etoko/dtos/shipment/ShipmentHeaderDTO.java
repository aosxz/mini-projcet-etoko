package com.miniproject.etoko.dtos.shipment;

import com.miniproject.etoko.models.Shipment;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShipmentHeaderDTO implements Serializable {
    private final String nameShipment;
    private final String description;
    private final BigDecimal cost;

    public static ShipmentHeaderDTO set(Shipment shipment) {
        return new ShipmentHeaderDTO(
                shipment.getNameShipment(),
                shipment.getDescription(),
                shipment.getCost());
    }

    public static List<ShipmentHeaderDTO> toList(List<Shipment> shipments) {
        List<ShipmentHeaderDTO> result = new ArrayList<>();
        for (Shipment shipment : shipments) {
            result.add(set(shipment));
        }
        return result;
    }
}