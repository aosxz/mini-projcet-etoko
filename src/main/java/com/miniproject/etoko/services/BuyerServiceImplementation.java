package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.buyer.BuyerHeaderDTO;
import com.miniproject.etoko.dtos.buyer.UpsertBuyerDTO;
import com.miniproject.etoko.models.Buyer;
import com.miniproject.etoko.repositories.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuyerServiceImplementation implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;




    @Override
    public List<BuyerHeaderDTO> findAllBuyer(){
        List<Buyer> buyerList = buyerRepository.findAll();
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        List<BuyerHeaderDTO>buyerStream = buyerList.stream()
                .map(buyer -> new BuyerHeaderDTO(
                                buyer.getBuyerId(),
                                buyer.getFirstName(),
                                buyer.getLastName(),
                                formatTanggal.format(buyer.getBirthDate()),
                                buyer.getBirthPlace(),
                                buyer.getAddress()
                        )
                ).collect(Collectors.toList());

        return buyerStream;
    }

    @Override
    public List<BuyerHeaderDTO>findBuyerById(Integer id){
        Optional<Buyer> buyerList = buyerRepository.findById(id);
        buyerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan"));
        List<BuyerHeaderDTO> buyerStream =buyerList
        .stream()
                .map(buyer -> new BuyerHeaderDTO(
                                buyer.getBuyerId(),
                                buyer.getFirstName(),
                                buyer.getLastName(),
                                buyer.getBirthDate().toString(),
                                buyer.getBirthPlace(),
                                buyer.getAddress()
                        )
                ).collect(Collectors.toList());

        return buyerStream;


    }

    @Override
    public List<BuyerHeaderDTO> insertBuyer(UpsertBuyerDTO newBuyer){
        Buyer buyer = newBuyer.convert();
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        LocalDate tanggalLahir = LocalDate.parse(newBuyer.getBirthDate(), formatTanggal);
        if (tanggalLahir.isEqual(LocalDate.now()) || tanggalLahir.isAfter(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tanggal lahir tidak boleh lebih besar dari tanggal sekarang");
        }

        buyerRepository.save(buyer);
        return  BuyerHeaderDTO.toList(buyerRepository.findAll());
    }


    @Override
    public boolean updateBuyer(Integer buyerId, UpsertBuyerDTO buyerUpdateDTO){
        Buyer oldBuyer = buyerRepository.findById(buyerId).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        LocalDate tanggalLahir;
        if (buyerUpdateDTO.getBirthDate() == null){
//            tanggalLahir  = LocalDate.parse(buyerUpdateDTO.getBirthDate(), formatTanggal);
            tanggalLahir = oldBuyer.getBirthDate();
        }
        else{
            tanggalLahir = LocalDate.parse(buyerUpdateDTO.getBirthDate(), formatTanggal);
            if (tanggalLahir.isEqual(LocalDate.now()) || tanggalLahir.isAfter(LocalDate.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tanggal lahir tidak boleh lebih besar dari tanggal sekarang");
            }
        }
        oldBuyer.setFirstName(buyerUpdateDTO.getFirstName() == null ? oldBuyer.getFirstName() : buyerUpdateDTO.getFirstName());
        oldBuyer.setLastName(buyerUpdateDTO.getLastName() == null ? oldBuyer.getLastName() : buyerUpdateDTO.getLastName());
        oldBuyer.setBirthDate(buyerUpdateDTO.getBirthDate() == null ? oldBuyer.getBirthDate() : tanggalLahir);
        oldBuyer.setBirthPlace(buyerUpdateDTO.getBirthPlace() == null ? oldBuyer.getBirthPlace() : buyerUpdateDTO.getBirthPlace());
        oldBuyer.setAddress(buyerUpdateDTO.getAddress()== null ? oldBuyer.getAddress() : buyerUpdateDTO.getAddress());


        buyerRepository.save(oldBuyer);
        return true;
    }


    @Override
    public boolean deleteBuyer(Integer buyerId){
        buyerRepository.findById(buyerId).orElseThrow(() -> new EntityNotFoundException("Buyer dengan Buyer ID "+buyerId+ " Tidak ada di database"));

        buyerRepository.deleteById(buyerId);
        return true;
    }

}
