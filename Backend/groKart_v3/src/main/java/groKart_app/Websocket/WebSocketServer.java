package groKart_app.Websocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
    
}
