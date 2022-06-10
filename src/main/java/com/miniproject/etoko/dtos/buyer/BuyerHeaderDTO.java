package com.miniproject.etoko.dtos.buyer;

import com.miniproject.etoko.models.Buyer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class BuyerHeaderDTO implements Serializable {
    private final Integer buyerId;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String birthPlace;
    private final String address;

    public static BuyerHeaderDTO set(Buyer buyer) {
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        return new BuyerHeaderDTO(
                buyer.getBuyerId(),
                buyer.getFirstName(),
                buyer.getLastName(),
                formatTanggal.format(buyer.getBirthDate()),
                buyer.getBirthPlace(),
                buyer.getAddress());
    }

    public static List<BuyerHeaderDTO> toList(List<Buyer> buyers){
        List<BuyerHeaderDTO> result = new ArrayList<>();
        for (Buyer buyer : buyers) {
            result.add(set(buyer));
        }
        return result;
    }
}
