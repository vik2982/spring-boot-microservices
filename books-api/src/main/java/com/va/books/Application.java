package com.va.books;

import com.va.books.model.Book;
import com.va.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class Application {

  @Autowired
  BookRepository bookRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /*
   * Init db data. TODO init via sql - https://www.baeldung.com/spring-boot-h2-database
   */
  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) throws ParseException {
    bookRepository.save(new Book("LOTR", "J.R Tolkien", 1980, 100, 9.99));
    bookRepository.save(new Book("Harry Potter", "J. K. Rowling", 1990, 200, 19.99));
    bookRepository.save(new Book("Giant Peach", "Roald Dahl", 2000, 300, 29.99));
  }
}
