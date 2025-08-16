package com.va.books.client;

import com.va.books.exception.BookException;
import com.va.books.model.Book;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ResilientRestClient {

    private static final Logger logger = LogManager.getLogger(ResilientRestClient.class);

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${mock-path}")
    private String path;

    /*private static String HOST;
    private static String PORT;
    private static String PATH;*/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private RetryRegistry registry;

    @PostConstruct
    public void postConstruct() {
        registry
                .retry("mockServiceCallRetry")
                .getEventPublisher()
                .onRetry(event -> logger.error(event));
    }

    public ResponseEntity<Book> getBooksFromMockServer(boolean error){
        return restTemplate.exchange(getUri(error,false), HttpMethod.GET, getEntity(), Book.class);
    }

    @Retry(name = "mockServiceCallRetry", fallbackMethod = "fallbackOnException")
    public ResponseEntity<Book> getBooksFromMockServerWithRetry(boolean error, boolean badRequest) throws BookException {
        return restTemplate.exchange(getUri(error,badRequest), HttpMethod.GET, getEntity(), Book.class);
    }

    public ResponseEntity<Book> fallbackOnException(boolean error, boolean badRequest, Exception ex) throws BookException {
        throw new BookException("Error when calling mock service");
    }

    public HttpEntity<String> getEntity() {
        // Set up headers for the request (optional, but good practice for JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON); // For GET, Content-Type is less critical, but can be included

        // Create an HttpEntity with headers (no body for GET requests)
        return new HttpEntity<>(headers);
    }

    public String getUri(boolean error, boolean badRequest){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append(path);

        if (!error)
            return sb.toString();

        /*Map<String, String> params = Map.of(
                "error", String.valueOf(error),
                "badRequest", String.valueOf(badRequest));

        // Convert simple Map to MultiValueMap
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        params.forEach((key, value) -> queryParams.add(key, value));*/

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(sb.toString())
                .queryParam("error", String.valueOf(error))
                .queryParam("badRequest", String.valueOf(badRequest));

        return uriBuilder.toUriString();
    }



    /*@Value("${host}")
    public void setHost(String host) {
        HOST = host;
    }

    @Value("${port}")
    public void setPort(String port) {
        PORT = port;
    }

    @Value("${mock-path}")
    public void setPath(String path) {
        PATH = path;
    }

    private static String getUri(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(HOST);
        sb.append(":");
        sb.append(PORT);
        sb.append(PATH);

        return sb.toString();
    }*/

}
