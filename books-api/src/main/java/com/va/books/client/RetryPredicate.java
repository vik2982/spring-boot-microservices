package com.va.books.client;

import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Predicate;

public class RetryPredicate implements Predicate<Exception> {
    @Override
    public boolean test(Exception e) {
        if (e instanceof HttpClientErrorException){
            return false;
        }
        return true;
    }
}
