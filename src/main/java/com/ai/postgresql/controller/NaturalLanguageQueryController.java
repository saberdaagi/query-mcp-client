package com.ai.postgresql.controller;

import com.ai.postgresql.model.PromptRequest;
import com.ai.postgresql.service.NaturalLanguageQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller responsible for handling user requests to convert natural language queries
 * into PostgreSQL-compatible SQL queries.
 *
 * <p>This controller exposes an API endpoint that accepts natural language input from users,
 * delegates the query processing to the {@link NaturalLanguageQueryService}, and returns
 * the results in a clean JSON format.</p>
 *
 * <p>Key features:
 * <ul>
 *     <li>Validates incoming requests to ensure non-empty queries.</li>
 *     <li>Delegates query processing to the service layer for SQL generation and execution.</li>
 *     <li>Returns results in JSON format with a concise explanation.</li>
 * </ul></p>
 *
 * @author Daagi Saber
 * @since 1.0
 */
@RestController
@RequestMapping("/api/natural-language-query")
@RequiredArgsConstructor
@Tag(name = "Natural Language Query Interface", description = "Converts natural language queries into SQL for PostgreSQL")
public class NaturalLanguageQueryController {

    /**
     * Service used to process natural language queries and generate SQL results.
     */
    private final NaturalLanguageQueryService naturalLanguageQueryService;

    /**
     * Endpoint to process a natural language query and return the corresponding SQL results.
     *
     * <p>This method accepts a natural language query from the user, validates it, and delegates
     * the processing to the {@link NaturalLanguageQueryService}. The response includes the SQL
     * query results and a brief explanation in JSON format.</p>
     *
     * @param request A {@link PromptRequest} containing the user's natural language query.
     * @return A {@link ResponseEntity} containing the SQL query results and explanation in JSON format,
     * or a bad request response if the query is invalid.
     */
    @Operation(
            summary = "Process natural language query",
            description = "Converts a natural language query into SQL and executes it against the PostgreSQL database.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Natural language query input",
                    content = @Content(schema = @Schema(implementation = PromptRequest.class))
            )
    )
    @PostMapping("/process")
    public ResponseEntity<String> processQuery(
            @Valid @RequestBody PromptRequest request) {

        String naturalLanguageQuery = request.prompt();
        if (naturalLanguageQuery == null || naturalLanguageQuery.isBlank()) {
            return ResponseEntity.badRequest().body("The natural language query cannot be empty.");
        }

        String response = naturalLanguageQueryService.processNaturalLanguageQuery(naturalLanguageQuery);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}