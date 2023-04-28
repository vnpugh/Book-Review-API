package com.example.bookreview.model;

import javax.persistence.*;

@Entity
@Table(name = "bestsellers")
public class BestSeller {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String genre;
    @Column
    private double rating;
    @Column
    private Integer weeks;
    @Column
    private Integer sales;



}
