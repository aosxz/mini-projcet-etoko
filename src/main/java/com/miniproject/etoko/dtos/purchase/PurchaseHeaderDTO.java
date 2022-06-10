package com.miniproject.etoko.dtos.purchase;

import com.miniproject.etoko.dtos.product.ProductHeaderDTO;
import com.miniproject.etoko.models.Product;
import com.miniproject.etoko.models.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PurchaseHeaderDTO implements Serializable {
    private  String purchaseId;
    private  Integer buyerId;
    private   String nameShipment;
    private   String productId;
    private   String purchaseDate;
    private  Integer quantity;
    private  String totalPrice;



    public static PurchaseHeaderDTO set(Purchase purchase) {
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        Locale locale = new Locale("id","ID");
        NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(locale);
        return new PurchaseHeaderDTO(
                purchase.getPurchaseId(),
                purchase.getBuyerId().getBuyerId(),
                purchase.getNameShipment().getNameShipment(),
                purchase.getProductId().getProductId(),
                formatTanggal.format(purchase.getPurchaseDate()),
                purchase.getQuantity(),
//                formatCurrency.format(purchase.getProductId().getPrice())
//                formatCurrency.getSymbol() + purchase.getProductId().getPrice()
//               purchase.getProductId().getPrice()
                formatCurrency.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())));
//                formatCurrency.format(purchase.getProductId().getPrice())


    }

    public static List<PurchaseHeaderDTO> toList(List<Purchase> purchases){
        List<PurchaseHeaderDTO> result = new ArrayList<>();
        for (Purchase purchase :purchases){
            result.add(set(purchase));
        }
        return result;

    }








}
