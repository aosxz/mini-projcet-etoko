package com.miniproject.etoko.services;


import com.miniproject.etoko.dtos.buyer.BuyerHeaderDTO;
import com.miniproject.etoko.dtos.buyer.UpsertBuyerDTO;

import java.util.List;

public interface BuyerService {

    public List<BuyerHeaderDTO> findAllBuyer();

    List<BuyerHeaderDTO>findBuyerById(Integer id);

    public List<BuyerHeaderDTO> insertBuyer(UpsertBuyerDTO newBuyer);

    public boolean updateBuyer(Integer buyerId, UpsertBuyerDTO buyerUpdateDTO);


    public boolean deleteBuyer(Integer buyerId);
}
