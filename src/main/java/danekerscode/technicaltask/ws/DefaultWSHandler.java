package danekerscode.technicaltask.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.technicaltask.repository.AmazonFileRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class DefaultWSHandler extends TextWebSocketHandler {

    private final AmazonFileRepository fileRepository;
    private final ObjectMapper objectMapper;

    public DefaultWSHandler(AmazonFileRepository fileRepository, ObjectMapper objectMapper) {
        this.fileRepository = fileRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleTextMessage(
            @NonNull WebSocketSession session,
            @NonNull TextMessage message
    ) throws Exception {
        var jsonNode = objectMapper.readTree(message.getPayload());

        if (jsonNode.has("command") && jsonNode.get("command").asText().equals("fileInfo")) {
            String fileName = jsonNode.get("fileName").asText();
            var amazonFile = fileRepository.findAmazonFileByFileName(fileName);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(amazonFile)));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("it was messages");
        super.handleMessage(session, message);
    }
}
