package danekerscode.technicaltask.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.technicaltask.mapper.AmazonFileMapper;
import danekerscode.technicaltask.repository.AmazonFileRepository;
import danekerscode.technicaltask.ws.DefaultWSHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ObjectMapper objectMapper;
    private final AmazonFileRepository amazonFileRepository;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/nts")
                .setAllowedOriginPatterns("*").withSockJS();
    }

//    @Bean
//    public TextWebSocketHandler textWebSocketHandler() {
//        return new DefaultWSHandler(amazonFileRepository, objectMapper);
//    }


}