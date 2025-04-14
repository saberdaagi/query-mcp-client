package com.ai.postgresql.service;

import com.ai.postgresql.model.NaturalLanguageQueryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service responsible for processing natural language queries into executable PostgreSQL queries.
 *
 * <p>This service leverages an AI-powered ChatClient to:
 * <ul>
 *     <li>Interpret natural language input.</li>
 *     <li>Generate accurate SQL queries.</li>
 *     <li>Return results in a clean JSON format with a concise explanation.</li>
 * </ul>
 *
 * <p>The system uses a predefined prompt to guide the AI in generating SQL queries and formatting responses.</p>
 *
 * @author Daagi Saber
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NaturalLanguageQueryService {

    /**
     * The system prompt that defines the AI's role and behavior when processing user input.
     *
     * <p>This prompt instructs the AI to:
     * <ul>
     *     <li>Generate accurate SQL queries using the 'query' tool.</li>
     *     <li>Process actual PostgreSQL database results.</li>
     *     <li>Return clean JSON-formatted output.</li>
     *     <li>Provide a concise explanation of the results.</li>
     * </ul>
     */
    @Value("${nlq.server-postgres.systemPrompt}")
    private String systemPrompt ;

    /**
     * ChatClient instance used to communicate with the AI model.
     */
    private final ChatClient chatClient;

    /**
     * Processes a natural language query and converts it into an executable PostgreSQL query.
     *
     * <p>This method delegates the natural language query to the AI model via the ChatClient,
     * which generates an SQL query and processes the response. The result is returned in
     * JSON format, along with a brief explanation of the output.</p>
     *
     * @param naturalLanguageQuery A natural language query such as "Show me all products under 1000".
     * @return A JSON-formatted response containing the SQL query results and an explanation.
     * @throws NaturalLanguageQueryException If the query cannot be processed due to an error.
     */
    public String processNaturalLanguageQuery(String naturalLanguageQuery) {
        if (naturalLanguageQuery == null || naturalLanguageQuery.isBlank()) {
            log.warn("Received an empty or null natural language query.");
            throw new NaturalLanguageQueryException("The natural language query cannot be empty or null.");
        }

        try {
            log.debug("Processing natural language query: {}", naturalLanguageQuery);

            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(naturalLanguageQuery)
                    .call()
                    .content();

            log.debug("Successfully generated AI response for query: {}", naturalLanguageQuery);
            return response;

        } catch (Exception ex) {
            log.error("Error occurred while processing natural language query: {}", naturalLanguageQuery, ex);
            throw new NaturalLanguageQueryException("An error occurred while processing your query.", ex);
        }
    }
}