package com.ai.postgresql.exception;

import com.ai.postgresql.model.NaturalLanguageQueryException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

/**
 * {@code GlobalExceptionHandler} is a centralized exception handler that intercepts
 * and manages exceptions thrown by the application, ensuring consistent error
 * responses across all REST endpoints.
 * <p>
 * This class is annotated with {@link RestControllerAdvice}, making it a global
 * exception handler for the entire application. It is specifically designed to
 * catch and handle exceptions like {@link NaturalLanguageQueryException}, returning
 * an appropriate HTTP response with a meaningful error message.
 * </p>
 * <p>
 * By defining custom exception handling in one central place, this class promotes
 * cleaner code and helps ensure that error responses are consistently formatted
 * across the system.
 * </p>
 * <p>
 * This handler uses {@link ProblemDetail}, which adheres to RFC 7807 for standardizing
 * problem details in HTTP responses.
 * </p>
 *
 * @author Daagi Saber
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link NaturalLanguageQueryException}, which may occur during the processing of
     * natural language queries and their conversion into SQL commands.
     * <p>
     * This method catches exceptions related to query processing and returns an HTTP response
     * with a status code of 500 (Internal Server Error), along with a {@link ProblemDetail}
     * containing the error details such as the error type and message.
     * </p>
     *
     * @param ex      the {@link NaturalLanguageQueryException} that was thrown
     * @param request the {@link HttpServletRequest} that triggered the error
     * @return a {@link ResponseEntity} containing a {@link ProblemDetail} with error details
     */
    @ExceptionHandler(NaturalLanguageQueryException.class)
    public ResponseEntity<ProblemDetail> handleQueryProcessingException(NaturalLanguageQueryException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create("uri:mcpassistant:query-error"));
        problemDetail.setTitle("Query Processing Error");
        problemDetail.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}


