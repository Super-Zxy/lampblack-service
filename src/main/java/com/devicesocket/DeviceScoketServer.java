//package com.devicesocket;
//
//import com.service.DeviceService;
//import com.socket.DeviceServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zxy
// * @date 2021/10/2 10:50
// * @description
// */
//@Component
//public class DeviceScoketServer implements CommandLineRunner {
//    private static String tag = "DeviceScoketServer====";
//
//    /**
//     * 日志
//     */
//    private final  Logger logger = LoggerFactory.getLogger(DeviceScoketServer.class);
//
//    @Autowired
//    private DeviceService deviceService;
//
//    //监听的端口号
//    @Value("${socket.port}")
//    public int port = 8068;
//    //线程池 - 保持线程数 20
//    @Value("${socket.pool-keep}")
//    public int poolKeep=20;
//    //线程池 - 核心线程数 10
//    @Value("${socket.pool-core}")
//    public int poolCore=10;
//    //线程池 - 最大线程数 30
//    @Value("${socket.pool-max}")
//    public int poolMax=30;
//    //线程队列容量 10
//    @Value("${socket.pool-queue-init}")
//    public int poolQueueInit=10;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        ServerSocket server = null;
//        Socket socket = null;
//        server = new ServerSocket(port);
//        logger.info("设备服务器已经开启, 监听端口:" + port);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(
//                this.poolCore,
//                this.poolMax,
//                this.poolKeep,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(this.poolQueueInit),
//                new ThreadPoolExecutor.DiscardOldestPolicy()
//        );
//        while (true) {
//            socket = server.accept();
//            pool.execute(new ServerConfig(socket,deviceService));
//        }
//    }
//
//}
