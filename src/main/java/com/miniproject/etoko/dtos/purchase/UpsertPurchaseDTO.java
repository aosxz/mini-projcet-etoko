package com.miniproject.etoko.dtos.purchase;


import com.miniproject.etoko.models.Buyer;
import com.miniproject.etoko.models.Product;
import com.miniproject.etoko.models.Purchase;
import com.miniproject.etoko.models.Shipment;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class UpsertPurchaseDTO {

    private String purchaseId;
    private  Integer buyerId;
    private  String nameShipment;
    private  String productId;
    private  LocalDate purchaseDate;
    private  Integer quantity;



    public Purchase convertPurchase(Buyer buyer, Shipment shipment, Product product){
        return new Purchase(
                purchaseId,
                buyer,
                shipment,
                product,
               LocalDate.now(),
                quantity);
    }

//    public Purchase convert(Buyer buyer , Shipment shipment , Product product) {
//        return new Purchase(
//                buyer,
//                shipment,
//                product,
//                purchaseDate,
//                quantity);
//    }
}
