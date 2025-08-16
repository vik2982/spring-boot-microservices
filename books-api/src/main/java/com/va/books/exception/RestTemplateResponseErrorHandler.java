package com.va.books.exception;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger logger = LogManager.getLogger(RestTemplateResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is5xxServerError() ||
                httpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        logger.error("Response Headers {} Body {} Status '{}'", response.getHeaders(), IOUtils.toString(response.getBody()), response.getStatusCode().toString());

        if (response.getStatusCode().is5xxServerError()) {
            throw new HttpServerErrorException(response.getStatusCode());
        } else if (response.getStatusCode().is4xxClientError()) {
            throw new HttpClientErrorException(response.getStatusCode());

        }
    }
}
