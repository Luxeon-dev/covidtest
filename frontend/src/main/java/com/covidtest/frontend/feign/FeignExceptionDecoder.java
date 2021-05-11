package com.covidtest.frontend.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Decoder of FeignExceptions to transfer HTTP codes from the microservices to the frontend
 */
public class FeignExceptionDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
    }
}
