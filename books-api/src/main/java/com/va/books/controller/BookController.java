package com.va.books.controller;

import com.va.books.client.ResilientRestClient;
import com.va.books.exception.BookException;
import com.va.books.model.Book;
import com.va.books.repository.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

  private static final Logger logger = LoggerFactory.getLogger(BookController.class);
  private static final String PRICE = "price";
  private static final String ASC = "asc";
  private static final String DESC = "desc";

  @Autowired
  BookRepository bookRepository;

  @Autowired
  ResilientRestClient restClient;

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBook(@PathVariable("id") Long id){

    Optional<Book> book = bookRepository.findById(id);
    if (book.isPresent())
      return new ResponseEntity<Book>(book.get(), HttpStatus.OK);

    return new ResponseEntity<Book>(HttpStatus.OK);

  }

  @GetMapping("/filter")
  //For now api only filters by title
  public ResponseEntity<Book> filter(@RequestParam("title") String title) {

    Optional<Book> book = Optional.ofNullable(bookRepository.findByTitle(title));

    if (book.isPresent())
      return new ResponseEntity<Book>(book.get(), HttpStatus.OK);

    return new ResponseEntity<Book>(HttpStatus.OK);
  }

  @GetMapping("/sort")
  public ResponseEntity<List<Book>> sort(@RequestParam("sort_by") String sortBy, @RequestParam("order_by") String orderBy)
      throws BookException {

    //For now api only handles sort by price
    if (!sortBy.equalsIgnoreCase(PRICE)){
      logger.error("Invalid sortBy query param");
      throw new BookException("Invalid sort_by query param");
    }

    if (orderBy.equalsIgnoreCase(ASC)) {
      return new ResponseEntity<List<Book>>(
          bookRepository.findByOrderByPriceAsc(), HttpStatus.OK);
    } else if (orderBy.equalsIgnoreCase(DESC)) {
      return new ResponseEntity<List<Book>>(
          bookRepository.findByOrderByPriceDesc(), HttpStatus.OK);
    }

    logger.error("Invalid orderBy query param");
    throw new BookException("Invalid order_by query param");
  }

  @PostMapping
  public ResponseEntity<List<Book>> createBook(@RequestBody Book book)
      throws BookException {

    List<Book> books = bookRepository.findAll();
    if (books.contains(book)) {
      logger.error("Cannot create book as it already exists");
      throw new BookException("Book already exists");
    }

    bookRepository.save(book);
    bookRepository.flush();

    return new ResponseEntity<List<Book>>(bookRepository.findAll(), HttpStatus.CREATED);
  }

  @GetMapping("/mock-api/{id}")
  public ResponseEntity<Book> getBooks(@PathVariable("id") Long id, @RequestParam(name = "error", defaultValue = "false") boolean error
          , @RequestParam(name = "retry", defaultValue = "false") boolean retry, @RequestParam(name = "badRequest", defaultValue = "false") boolean badRequest) throws BookException {

    //If error request param is false mock api returns book. Else mock api returns error.  If retry request param is true mock api call is retried
    Optional<Book> book = !error ? Optional.ofNullable(restClient.getBooksFromMockServer(false).getBody()) : retry ? Optional.ofNullable(restClient.getBooksFromMockServerWithRetry(true,badRequest).getBody()) : Optional.ofNullable(restClient.getBooksFromMockServer(true).getBody());

    if (book.isPresent())
      return new ResponseEntity<Book>(book.get(), HttpStatus.OK);

    return new ResponseEntity<Book>(HttpStatus.OK);

  }

}
