package me.onee.user.thrift;

import me.onee.thrift.message.MessageService;
import me.onee.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取 thrift 服务
 * Created by VOREVER
 * Date: 2018/12/29 16:41
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String userServerIp;

    @Value("${thrift.user.port}")
    private int userServerPort;

    @Value("${thrift.message.ip}")
    private String messageServerIp;

    @Value("${thrift.message.port}")
    private int messageServerPort;

    private enum ServiceType {
        USER,
        MESSAGE
    }

    // 获取用户服务
    public UserService.Client getUserService() {
        return getService(userServerIp, userServerPort, ServiceType.USER);
    }

    // 获取消息服务
    public MessageService.Client getMessageService() {
        return getService(messageServerIp, messageServerPort, ServiceType.MESSAGE);
    }

    private <T> T getService(String ip, int port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip, port, 3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient client = null;
        switch (serviceType) {
            case USER:
                client = new UserService.Client(protocol);
                break;
            case MESSAGE:
                client = new MessageService.Client(protocol);
                break;
        }
        return (T) client;
    }

}
