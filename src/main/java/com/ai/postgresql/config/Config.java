package com.ai.postgresql.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the AI chat client used to process
 * natural language prompts and translate them into PostgreSQL queries.
 *
 * <p>This configuration wires together the {@link ChatClient} with a
 * {@link SyncMcpToolCallbackProvider}, which is responsible for injecting
 * tool support (e.g., SQL query execution capabilities) into the AI pipeline.</p>
 *
 * <p>Spring will automatically detect this configuration and manage the lifecycle
 * of the {@code ChatClient} as a singleton bean in the application context.</p>
 *
 * <p>This setup is essential for enabling tool-enhanced AI behaviors through the
 * Model-Controlled Prompting (MCP) framework.</p>
 *
 * @author Daagi Saber
 * @since 1.0
 */
@Configuration
public class Config {

    /**
     * Creates and configures a {@link ChatClient} bean using the builder pattern and
     * tool callback provider from the MCP framework.
     *
     * <p>The {@code ChatClient} is the main interface used by the application to send
     * system and user prompts to the underlying AI model. The injected {@code SyncMcpToolCallbackProvider}
     * enables dynamic tool binding, allowing the AI to invoke predefined actions like database querying.</p>
     *
     * @param chatClientBuilder the builder used to construct a {@code ChatClient} instance
     * @param mcpTools the tool callback provider for model-controlled tool invocation
     * @return a fully configured and tool-aware {@link ChatClient} bean
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, SyncMcpToolCallbackProvider mcpTools) {
        return chatClientBuilder
                .defaultTools(mcpTools)
                .build();
    }
}
