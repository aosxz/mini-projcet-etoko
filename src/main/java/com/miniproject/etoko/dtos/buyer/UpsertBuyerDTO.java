package com.miniproject.etoko.dtos.buyer;

import com.miniproject.etoko.models.Buyer;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class UpsertBuyerDTO {

    private  String firstName;
    private  String lastName;
    private  String birthDate;
    private  String birthPlace;
    private  String address;

    public Buyer convert(){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        return new Buyer(
                firstName,
                lastName,
                LocalDate.parse(birthDate, formatTanggal),
                birthPlace,
                address);
    }


}
