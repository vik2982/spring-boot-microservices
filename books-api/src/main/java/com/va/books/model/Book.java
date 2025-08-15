package com.va.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
