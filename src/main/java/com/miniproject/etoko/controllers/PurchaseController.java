package com.miniproject.etoko.controllers;

import com.miniproject.etoko.dtos.RestResponse;
import com.miniproject.etoko.dtos.buyer.BuyerHeaderDTO;
import com.miniproject.etoko.dtos.purchase.PurchaseHeaderDTO;
import com.miniproject.etoko.dtos.purchase.UpsertPurchaseDTO;
import com.miniproject.etoko.services.PurchaseService;
import com.miniproject.etoko.services.PurchaseServiceImplementatiton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("getAll")
    public ResponseEntity<RestResponse<List<PurchaseHeaderDTO>>> getAllPurchase(){
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findAllPurchase(),
                        "Purchase berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findByPurchaseDate/{purchaseDate}")
    public ResponseEntity<RestResponse<List<PurchaseHeaderDTO>>> findByPurchaseDate(@PathVariable String purchaseDate){
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByDate(purchaseDate),
                        "Purchase dengan tanggal" + purchaseDate + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByBuyerId/{buyerId}")
    public ResponseEntity<RestResponse<List<PurchaseHeaderDTO>>> findPurchaseByBuyerId(@PathVariable Integer buyerId){
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByBuyerId(buyerId),
                        "Purchase dengan buyer ID " + buyerId + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByPurchaseId/{purchaseId}")
    public ResponseEntity<RestResponse<PurchaseHeaderDTO>> findPurchaseByPurchaseId(@PathVariable String purchaseId){
        return new ResponseEntity(
                new RestResponse<>(purchaseService.findPurchaseById(purchaseId),
                        "Purchase dengan ID " + purchaseId + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByShipment/{nameShipment}")
    public ResponseEntity<RestResponse<List<PurchaseHeaderDTO>>> findPurchaseByShipment(@PathVariable String nameShipment){
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByShipment(nameShipment),
                        "Purchase dengan shipment " + nameShipment + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByCategoryProduct/{category}")
    public ResponseEntity<RestResponse<List<PurchaseHeaderDTO>>> findPurchaseByCategoryProduct(@PathVariable String category){
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByCategoryProduct(category),
                        "Purchase dengan category " + category + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }


    @PostMapping("insert")
    public ResponseEntity<RestResponse<BuyerHeaderDTO>> insertPurchase(@RequestBody UpsertPurchaseDTO newPurchase){
        return new ResponseEntity(
                new RestResponse<>(purchaseService.insertPurchase(newPurchase),
                        "Purchase berhasil ditambahkan",
                        201),
                HttpStatus.CREATED);
    }

    @PutMapping("update/{purchaseId}")
    public ResponseEntity<RestResponse<BuyerHeaderDTO>> updatePurchase(@PathVariable String purchaseId, @RequestBody UpsertPurchaseDTO newPurchase){
        return new ResponseEntity(
                new RestResponse<>(purchaseService.updatePurchase(purchaseId, newPurchase),
                        "Purchase "+purchaseId+ " berhasil diubah",
                        200),
                HttpStatus.OK);
    }

    @DeleteMapping("delete/{purchaseId}")
    public ResponseEntity<RestResponse<BuyerHeaderDTO>> deletePurchase(@PathVariable String purchaseId){
        return new ResponseEntity(
                new RestResponse<>(purchaseService.deletePurchase(purchaseId),
                        "Purchase "+ purchaseId +" berhasil dihapus",
                        200),
                HttpStatus.OK);
    }










}
