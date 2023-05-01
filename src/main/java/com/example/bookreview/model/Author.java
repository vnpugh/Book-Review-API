package com.example.bookreview.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.List;

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

    /**
     * OneToMany relationship between Author and Book.
     * One Author can have many Books.
     * One Book can have only one Author.
     */
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
     private List<Book> books;
    //private List<Book> books = new ArrayList<>();


    public Author(Long id, String name, String country, Integer age, String genre) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.age = age;
        this.genre = genre;
    }

    public Author() {
    }

    public String getName() {
        return name;
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
