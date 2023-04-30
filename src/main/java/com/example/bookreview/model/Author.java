package com.example.bookreview.model;


import javax.persistence.*;

@Entity
@Table(name = "authors") // SQL table name
public class Author {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this is our PK
    private Long id;

    @Column
    private String name;
    @Column
    private String country;
    @Column
    private Integer age;
    @Column
    private String genre;

    public Author(Long id, String name, String country, Integer age, String genre) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.age = age;
        this.genre = genre;
    }

    public Author() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", age=" + age +
                ", genre='" + genre + '\'' +
                '}';
    }
}
