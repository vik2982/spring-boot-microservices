package com.va.books.client;

import com.va.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ResilientRestClient {

    /*@Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${path}")
    private String path;*/

    private static String HOST;
    private static String PORT;
    private static String PATH;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Book> retryGetBooksFromMockServer(){

        /*StringBuilder sb = new StringBuilder(); // Create a StringBuilder object
        sb.append("http://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append(path);

        String url = sb.toString();*/

        // Set up headers for the request (optional, but good practice for JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON); // For GET, Content-Type is less critical, but can be included

        // Create an HttpEntity with headers (no body for GET requests)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(getUri())
                .queryParam("error", "true");

        return restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, Book.class);

    }

    public ResponseEntity<Book> getBooksFromMockServer(){

        // Set up headers for the request (optional, but good practice for JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON); // For GET, Content-Type is less critical, but can be included

        // Create an HttpEntity with headers (no body for GET requests)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(getUri(), HttpMethod.GET, entity, Book.class);

    }

    @Value("${host}")
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
    }

}
