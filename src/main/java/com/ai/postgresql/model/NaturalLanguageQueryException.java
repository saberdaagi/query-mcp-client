package com.ai.postgresql.model;

/**
 * {@code QueryProcessingException} is a custom runtime exception that represents
 * failures occurring during the processing of a natural language query into a
 * PostgreSQL command or result.
 * <p>
 * This exception is typically thrown by service layer components when an error
 * occurs while interacting with the AI model or transforming input/output data.
 * </p>
 *
 * <p>Examples of usage:</p>
 * <pre>{@code
 * throw new QueryProcessingException("AI service unreachable");
 * }</pre>
 *
 * @author Daagi Saber
 * @since 1.0
 */
public class NaturalLanguageQueryException extends RuntimeException {

    /**
     * Constructs a new {@code QueryProcessingException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NaturalLanguageQueryException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code QueryProcessingException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the underlying cause of this exception
     */
    public NaturalLanguageQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}