package me.onee.user.thrift;

import me.onee.thrift.user.UserService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by VOREVER
 * Date: 2018/12/29 13:19
 */
@Configuration
public class ThriftServer {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private UserService.Iface userService;

    @PostConstruct
    public void startThriftServer() {
        // 初始化处理器
        TProcessor processor = new UserService.Processor<>(userService);

        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(serverPort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        args.processor(processor);
        // 定义传输方式为帧传输
        args.transportFactory(new TFramedTransport.Factory());
        // 定义协议为二进制协议
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);
        server.serve(); // 启动 thrift 服务
    }

}
