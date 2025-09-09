package com.va.books.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String author;

  private int yearPublished;

  private int pages;

  private double price;

  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  public Book(String title, String author, int yearPublished, int pages, double price) {
    this.title = title;
    this.author = author;
    this.yearPublished = yearPublished;
    this.pages = pages;
    this.price = price;
  }



  //hashcode and equals used by rest create method to check if book already exists
  public boolean equals(Object bookObj) {
    Book book = (Book) bookObj;
    return title.equalsIgnoreCase(book.getTitle());
  }

  public int hashCode() {
    return title.toUpperCase().hashCode();
  }

}
