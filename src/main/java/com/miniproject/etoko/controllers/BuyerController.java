package com.miniproject.etoko.controllers;

import com.miniproject.etoko.dtos.RestResponse;
import com.miniproject.etoko.dtos.buyer.BuyerHeaderDTO;
import com.miniproject.etoko.dtos.buyer.UpsertBuyerDTO;
import com.miniproject.etoko.services.BuyerService;
import com.miniproject.etoko.services.BuyerServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("buyer")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("find-all")
    public ResponseEntity<RestResponse<List<BuyerHeaderDTO>>> findAllBuyer() {
        return new ResponseEntity<>(
                new RestResponse<>(buyerService.findAllBuyer(),
                        "Buyer berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<RestResponse<List<BuyerHeaderDTO>>> findBuyerById(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new RestResponse<>(buyerService.findBuyerById(id),
                        "Buyer ID "+id+ " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }


    @PostMapping("insert")
    public ResponseEntity<RestResponse<BuyerHeaderDTO>> insertBuyer(@RequestBody UpsertBuyerDTO newBuyer) {
        return new ResponseEntity(
                new RestResponse<>(buyerService.insertBuyer(newBuyer),
                        "Buyer berhasil ditambahkan",
                        201),
                HttpStatus.CREATED);
    }


//    public ResponseEntity<List<BuyerHeaderDTO>> insertNewBuyer (@RequestBody UpsertBuyerDTO newBuyer){
//        return  ResponseEntity.ok().body(
//                buyerService.insertBuyer(newBuyer));
//    }

    @PutMapping("{buyerId}")
    public ResponseEntity<RestResponse>updateBuyer(@PathVariable Integer buyerId, @RequestBody UpsertBuyerDTO buyerUpdateDTO){
        return new ResponseEntity(
                new RestResponse(buyerService.updateBuyer(buyerId, buyerUpdateDTO),
                        "Buyer ID " + buyerId + " berhasil di update ",
                        201),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{buyerId}")
    public ResponseEntity<RestResponse> deleteBuyer(@PathVariable Integer buyerId){
        return new ResponseEntity(
                new RestResponse(buyerService.deleteBuyer(buyerId),
                        "Buyer ID " + buyerId + " berhasil dihapus ",
                        201),
                HttpStatus.OK);
    }
}
