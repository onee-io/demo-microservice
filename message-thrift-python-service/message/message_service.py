from api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

# 消息服务实现类
class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        # 只做服务调用演示 不实现具体功能
        print("Send mobile message -> mobile:" + mobile + " message:" + message)
        return True

    def sendEmailMessage(self, email, message):
        # 只做服务调用演示 不实现具体功能
        print("Send mobile message -> email:" + email + " message:" + message)
        return True


if __name__ == '__main__':
    handler = MessageServiceHandler()
    # 初始化消息处理器
    processor = MessageService.Processor(handler)
    # 指定服务端口
    transport = TSocket.TServerSocket("127.0.0.1", "9090")
    # 指定传输方式为帧传输
    tfactory = TTransport.TFramedTransportFactory()
    # 指定传输协议为二进制协议
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    # 实例化服务
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print("Python thrift server started")
    server.serve()
    print("Python thrift server exit")
