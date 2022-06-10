package com.miniproject.etoko.services;


import com.miniproject.etoko.dtos.purchase.PurchaseHeaderDTO;
import com.miniproject.etoko.dtos.purchase.UpsertPurchaseDTO;
import com.miniproject.etoko.models.Buyer;
import com.miniproject.etoko.models.Product;
import com.miniproject.etoko.models.Purchase;
import com.miniproject.etoko.models.Shipment;
import com.miniproject.etoko.repositories.BuyerRepository;
import com.miniproject.etoko.repositories.ProductRepository;
import com.miniproject.etoko.repositories.PurchaseRepository;
import com.miniproject.etoko.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImplementatiton implements PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;


    @Override
    public List<PurchaseHeaderDTO> findAllPurchase() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        Locale locale = new Locale("id", "ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
        List<PurchaseHeaderDTO> purchaseStream = purchaseList.stream()
                .map(purchase -> new PurchaseHeaderDTO(
                                purchase.getPurchaseId(),
                                purchase.getBuyerId().getBuyerId(),
                                purchase.getNameShipment().getNameShipment(),
                                purchase.getProductId().getProductId(),
                                formatTanggal.format(purchase.getPurchaseDate()),
                                purchase.getQuantity(),
                                formatDuit.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())))
                ).collect(Collectors.toList());
        return purchaseStream;

    }

    @Override
    public List<PurchaseHeaderDTO>findPurchaseById(String purchaseId){
        Optional<Purchase> purchaseList = Optional.ofNullable(purchaseRepository.findById(purchaseId).orElseThrow(() -> new EntityNotFoundException("Data tidak ditemukan")));
        Locale locale = new Locale("id", "ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
        List<PurchaseHeaderDTO> purchaseStream = purchaseList.stream()
                .map(purchase -> new PurchaseHeaderDTO(
                        purchase.getPurchaseId(),
                        purchase.getBuyerId().getBuyerId(),
                        purchase.getNameShipment().getNameShipment(),
                        purchase.getProductId().getProductId(),
                        formatTanggal.format(purchase.getPurchaseDate()),
                        purchase.getQuantity(),
                        formatDuit.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())))
                ).collect(Collectors.toList());
        return purchaseStream;
    }


    @Override
    public PurchaseHeaderDTO insertPurchase(UpsertPurchaseDTO newPurchase) {
        Buyer buyer = buyerRepository.findById(newPurchase.getBuyerId()).
                orElseThrow(() -> new EntityNotFoundException("Buyer Tidak Ditemukan"));

        Product product = productRepository.findById(newPurchase.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product Tidak Ditemukan"));

        Shipment shipment = shipmentRepository.findById(newPurchase.getNameShipment()).
                orElseThrow(() -> new EntityNotFoundException("Shimpent Tidak Ditemukan"));

        if (newPurchase.getQuantity() > product.getStock()) {
            throw new EntityNotFoundException("Quantity Melebihi Stock");
        }
        Purchase purchase = newPurchase.convertPurchase(buyer, shipment, product);
        product.setStock(product.getStock() - newPurchase.getQuantity());
        purchaseRepository.save(purchase);
        productRepository.save(product);


        return PurchaseHeaderDTO.set(purchase);

    }

    @Override
    public List<PurchaseHeaderDTO> findPurchaseByDate(String purchaseDate) {
        LocalDate localDate = LocalDate.parse(purchaseDate, DateTimeFormatter.ofPattern("dd-MMMM-yyyy"));
        purchaseRepository.getPurchaseByDate(localDate.toString());
        List<Purchase> purchaseList = purchaseRepository.getPurchaseByDate(localDate.toString());
        if (purchaseList.isEmpty()) {
            throw new EntityNotFoundException("Purchase Date Tidak Ditemukan");
        }

        return PurchaseHeaderDTO.toList(purchaseRepository.getPurchaseByDate(purchaseDate));
    }

    @Override
    public List<PurchaseHeaderDTO> findPurchaseByBuyerId(Integer buyerId) {
        List<Purchase> filterBuyerId = purchaseRepository.getPurchaseByBuyerId(buyerId);
        if (filterBuyerId.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan BuyerID "+ buyerId+ " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByBuyerId(buyerId);
        return PurchaseHeaderDTO.toList(purchaseRepository.getPurchaseByBuyerId(buyerId));

    }

    @Override
    public List<PurchaseHeaderDTO> findPurchaseByShipment(String nameShipment) {
        List<Purchase>filterPurchaseByShipment = purchaseRepository.getPurchaseByShipment(nameShipment);
        if (filterPurchaseByShipment.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan Shipment "+ nameShipment+ " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByShipment(nameShipment);
        return PurchaseHeaderDTO.toList(purchaseRepository.getPurchaseByShipment(nameShipment));
    }

    @Override
    public List<PurchaseHeaderDTO> findPurchaseByCategoryProduct(String category) {
        List<Purchase>filterPurchaseByCategoryProduct = purchaseRepository.getPurchaseByCategoryProduct(category);
        if (filterPurchaseByCategoryProduct.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan Category Product "+ category+ " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByCategoryProduct(category);
        return PurchaseHeaderDTO.toList(purchaseRepository.getPurchaseByCategoryProduct(category));
    }

    @Override
    public boolean updatePurchase(String purchaseId, UpsertPurchaseDTO upsertPurchaseDTO) {
        Purchase oldPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Tidak Ditemukan"));
        Product product = productRepository.findById(upsertPurchaseDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product Tidak Ditemukan"));


        oldPurchase.setQuantity(upsertPurchaseDTO.getQuantity() == null ? oldPurchase.getQuantity() : upsertPurchaseDTO.getQuantity());
        product.setStock(product.getStock() - upsertPurchaseDTO.getQuantity());
        purchaseRepository.save(oldPurchase);
        productRepository.save(product);

        return true;

    }

    @Override
    public boolean deletePurchase(String purchaseId) {
        purchaseRepository.findById(purchaseId)
                .orElseThrow((() -> new EntityNotFoundException("Purchase dengan Purchase ID " + purchaseId + " tidak ditemukan")));
        purchaseRepository.deleteById(purchaseId);
        return true;
    }
}
