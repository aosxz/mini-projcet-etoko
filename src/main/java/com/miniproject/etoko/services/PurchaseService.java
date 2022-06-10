package com.miniproject.etoko.services;

import com.miniproject.etoko.dtos.purchase.PurchaseHeaderDTO;
import com.miniproject.etoko.dtos.purchase.UpsertPurchaseDTO;

import java.util.List;

public interface PurchaseService {
    public List<PurchaseHeaderDTO> findAllPurchase();

    List<PurchaseHeaderDTO>findPurchaseById(String purchaseId);

    public PurchaseHeaderDTO insertPurchase(UpsertPurchaseDTO newPurchase);

    public List<PurchaseHeaderDTO> findPurchaseByDate(String purchaseDate);

    public List<PurchaseHeaderDTO> findPurchaseByBuyerId(Integer buyerId);

    public List<PurchaseHeaderDTO> findPurchaseByShipment(String nameShipment);

    public List<PurchaseHeaderDTO> findPurchaseByCategoryProduct(String category);

    public boolean updatePurchase(String purchaseId, UpsertPurchaseDTO upsertPurchaseDTO);

    public boolean deletePurchase(String purchaseId);
}
