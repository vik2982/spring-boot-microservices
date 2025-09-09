package com.va.books;

import com.va.books.model.Book;
import com.va.books.model.User;
import com.va.books.repository.BookRepository;
import com.va.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;

@SpringBootApplication
public class Application {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /*
   * Can also init via sql - https://www.baeldung.com/spring-boot-h2-database
   */
  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) throws ParseException {
    bookRepository.save(new Book("LOTR", "J.R Tolkien", 1980, 100, 9.99));
    bookRepository.save(new Book("Harry Potter", "J. K. Rowling", 1990, 200, 19.99));
    bookRepository.save(new Book("Giant Peach", "Roald Dahl", 2000, 300, 29.99));

    userRepository.save(new User("user1",passwordEncoder.encode("password1")));
    userRepository.save(new User("user2",passwordEncoder.encode("password2")));
    userRepository.save(new User("user3",passwordEncoder.encode("password3")));

  }
}
