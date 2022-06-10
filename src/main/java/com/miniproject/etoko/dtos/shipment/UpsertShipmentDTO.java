package com.miniproject.etoko.dtos.shipment;

import com.miniproject.etoko.models.Shipment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpsertShipmentDTO {
    private  String nameShipment;
    private  String description;
    private  BigDecimal cost;

    public Shipment convert() {
        return new Shipment(
                nameShipment,
                description,
                cost);
    }


}
