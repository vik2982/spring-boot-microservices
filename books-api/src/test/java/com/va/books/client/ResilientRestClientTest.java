package com.va.books.client;

import com.va.books.exception.BookException;
import com.va.books.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ResilientRestClient.class})
@EnableAutoConfiguration
@TestPropertySource(
        properties = """
            spring.cloud.config.enabled=false
            resilience4j.retry.instances.mockServiceCallRetry.maxAttempts=3
            resilience4j.retry.instances.mockServiceCallRetry.waitDuration=3000
            resilience4j.retry.instances.mockServiceCallRetry.retryExceptionPredicate=com.va.books.client.RetryPredicate
            mock.server.host=localhost
            mock.server.port=1080
            mock.server.path=/books-mock-api
        """)
public class ResilientRestClientTest {

    //Use @Autowired instead of @InjectMocks as we don't want to mock RetryRegistry - https://www.baeldung.com/spring-test-autowired-injectmocks
    @Autowired
    ResilientRestClient resilientRestClient;

    @MockitoBean
    RestTemplate restTemplate;

    @Test
    public void testGetBooksFromMockServerSuccess(){
        Book book = new Book("Harry Potter", "J. K. Rowling", 1990, 200, 19.99);
        when(restTemplate.exchange(anyString(),any(),any(),any(Class.class))).thenReturn(ResponseEntity.status(HttpStatus.OK).body(book));
        ResponseEntity<Book> response =  resilientRestClient.getBooksFromMockServer(false);
        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Harry Potter",response.getBody().getTitle());
    }

    @Test
    public void testGetBooksFromMockServerError(){
        when(restTemplate.exchange(anyString(),any(),any(),any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThrows(
                HttpServerErrorException.class,
                () -> resilientRestClient.getBooksFromMockServer(true)
        );
    }

    @Test
    public void testGetBooksFromMockServerWithRetry() throws BookException {
        when(restTemplate.exchange(anyString(),any(),any(),any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThrows(
                BookException.class,
                () -> resilientRestClient.getBooksFromMockServerWithRetry(false,false)
        );
        verify(restTemplate,times(3)).exchange(anyString(),any(),any(),any(Class.class));
    }

    @Test
    public void testGetBooksFromMockServerWithRetry4xxError() throws BookException {
        when(restTemplate.exchange(anyString(),any(),any(),any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        assertThrows(
                BookException.class,
                () -> resilientRestClient.getBooksFromMockServerWithRetry(false,true)
        );
        verify(restTemplate,times(1)).exchange(anyString(),any(),any(),any(Class.class));
    }
}
