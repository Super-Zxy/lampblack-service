//package com.devicesocket;
//
//import com.config.LampblackConstant;
//import com.entity.DeviceLampblackData;
//import com.service.DeviceService;
//import com.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.Socket;
//import java.net.SocketException;
//import java.util.Map;
//
///**
// * @author zxy
// * @date 2021/10/2 10:58
// * @description
// */
//public class ServerConfig extends Thread {
//
//    private static String tag = "ServerConfig====";
//
//    /**
//     * 日志
//     */
//    private final static Logger logger = LoggerFactory.getLogger(ServerConfig.class);
//
//    private DeviceService deviceService;
//
//    private Socket socket;
//
//    public ServerConfig(Socket socket, DeviceService deviceService) {
//        this.socket = socket;
//        this.deviceService = deviceService;
//    }
//
//    private void handle(InputStream inputStream) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        byte[] bytes = new byte[1024];
//        int len = inputStream.read(bytes);
//        StringBuffer clientInputStr = new StringBuffer();
//        clientInputStr.append(new String(bytes, 0, len, "UTF-8"));
//        logger.debug(tag + "油烟监控设备数据信息:" + clientInputStr);
//        logger.debug(tag + "from client ... " + clientInputStr + "当前线程" + Thread.currentThread().getName());
//
//        //解析油烟监控设备数据
//        DeviceLampblackData deviceLampblackData = new DeviceLampblackData();
//
//        //根据;分隔所有参数，与LampBlackMap匹配获取参数值
//        ServerConfig.findDeviceValueBySplit(deviceLampblackData, clientInputStr.toString());
//
//        //根据参数LampBlackMap获取报文中对应参数值
////            DeviceHandlerThread.findDeviceValueByKeyMap(deviceLampblackData,clientInputStr);
//
//        //入表
//
//        int nRet = deviceService.addDeviceLampBlackData(deviceLampblackData);
//    }
//
//    @Override
//    public void run() {
//        BufferedWriter writer = null;
//        try {
//            // 设置连接超时9秒
//            socket.setSoTimeout(9000);
//            logger.debug(tag + "客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
//            InputStream inputStream = socket.getInputStream();
//            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            try {
//                handle(inputStream);
//            } catch (IOException | IllegalArgumentException e) {
//                logger.error("油烟数据Socket服务器 run 异常: " + e.getMessage());
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        } catch (SocketException socketException) {
//            socketException.printStackTrace();
//            try {
//                writer.close();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //根据;分隔所有参数，与LampBlackMap匹配获取参数值
//    private static void findDeviceValueBySplit(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String[] deviceDataArray = clientInputStr.split(";");
//        for (String deviceData : deviceDataArray) {
//            String deviceDataKey = deviceData.substring(0, deviceData.indexOf("="));
//            String deviceDataKeyParam = LampblackConstant.deviceParams.get(deviceDataKey);
//            String deviceDataValue = deviceData.substring(deviceData.indexOf("=") + 1);
//            Class<?> clazz = deviceLampblackData.getClass();
//            Method method = clazz.getMethod("set" + captureName(deviceDataKeyParam), String.class);
//            method.invoke(deviceLampblackData, StringUtils.isNotEmpty(deviceDataValue) ? deviceDataValue : "0");
//        }
//    }
//
//    //根据参数LampBlackMap获取报文中对应参数值
//    private static void findDeviceValueByKeyMap(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        for (Map.Entry<String, String> entry : LampblackConstant.deviceParams.entrySet()) {
//            String deviceDataKey = entry.getKey();
//            String deviceDataKeyParam = entry.getValue();
//            String[] strTmpArray = clientInputStr.split(deviceDataKey + "=");
//            String deviceDataValue = "0";
//            if (strTmpArray.length > 1) {
//                String strTmp = strTmpArray[1];
//                deviceDataValue = strTmp.substring(0, strTmp.indexOf(";"));
//            }
//            Class<?> clazz = deviceLampblackData.getClass();
//            Method method = clazz.getMethod("set" + captureName(deviceDataKeyParam), String.class);
//            method.invoke(deviceLampblackData, StringUtils.isNotEmpty(deviceDataValue) ? deviceDataValue : "0");
//        }
//    }
//
//    //首字母大写
//    private static String captureName(String str) {
//        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
//        char[] cs = str.toCharArray();
//        cs[0] -= 32;
//        return String.valueOf(cs);
//    }
//}
