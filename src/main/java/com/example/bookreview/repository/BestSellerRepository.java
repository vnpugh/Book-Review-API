package com.example.bookreview.repository;

import com.example.bookreview.model.BestSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestSellerRepository extends JpaRepository<BestSeller, Long> {

    //find a bestselling book based on sales data
    BestSeller findBestSellerBySales(Integer sales);

    //find a bestselling book by # of weeks and rating
   BestSeller findBestSellerByWeeksAndRating(Integer weeks, Double rating);

   //retrieve all best sellers
    List<BestSeller> findAll();

}
