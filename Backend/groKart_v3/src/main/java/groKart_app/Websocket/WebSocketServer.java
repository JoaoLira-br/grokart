package groKart_app.Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

    //Store all the socket session the corresponding username of the one entered
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> userNameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException{
        logger.info("Entered on Open");

        //Map the username into their respective sessions
        sessionUsernameMap.put(session,username);
        userNameSessionMap.put(username,session);

        String message = "User:" + username + " has JOINED the chat";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        //TODO: Error Handling need to be done here
        logger.info("Entered into Error");
    }
    private void sendMessagetoAnUser(String username, String message){
        try{
            userNameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e){
            logger.info("Exception: "+ e.getMessage().toString());
            e.printStackTrace();
        }
    }
    private void broadcast(String message){
        sessionUsernameMap.forEach((session,username) ->{
            try{
                session.getBasicRemote().sendText(message);
            } catch (IOException e){
                logger.info("Exception: "+e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }


}
