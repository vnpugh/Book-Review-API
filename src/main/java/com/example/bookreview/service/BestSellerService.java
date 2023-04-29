package com.example.bookreview.service;


import com.example.bookreview.model.BestSeller;
import com.example.bookreview.repository.BestSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestSellerService {

    @Autowired
    private BestSellerRepository bestSellerRepository;

    @Autowired
    public void setBestSellerRepository(BestSellerRepository bestSellerRepository) {
        this.bestSellerRepository = bestSellerRepository;
    }

    public List<BestSeller> getAllBestSellers() {
        return bestSellerRepository.findAll();
    }




}
