package pro.franky.talentcareer.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WebSocket末端
 *
 * @author Steveny
 * @since 2023/7/1
 */
@Slf4j
@Component
@ServerEndpoint("/resume/process")
public class TalentCareerEndPoint {
    public static Map<String, TalentCareerEndPoint> END_POINTS = new HashMap<>();
    public Session session;
    private String username;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        var map = session.getRequestParameterMap();
        if (null != map) {
            List<String> stringList = map.get("username");
            if (null != stringList) {
                username = stringList.get(0);
                if (!END_POINTS.containsKey(username)) {
                    END_POINTS.put(username, this);
                } else {
                    END_POINTS.replace(username, this);
                }
                log.info("用户已连接 --> " + username);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        END_POINTS.remove(username);
        log.info("用户已下线 --> " + username);
    }

    /**
     * 发送消息
     *
     * @param username username
     * @param message message
     * @throws IOException IO错误
     */
    public static synchronized void sendMessage(String username, String message) throws IOException {
        TalentCareerEndPoint.END_POINTS.get(username).session.getBasicRemote().sendText(message);
    }
}
