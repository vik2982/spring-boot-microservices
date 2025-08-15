package com.va.books.repository;


import com.va.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Book findByTitle(String title);

  List<Book> findByOrderByPriceAsc();

  List<Book> findByOrderByPriceDesc();
}
