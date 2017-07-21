package ws.gm.system.httpServer.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by lee on 17-2-23.
 */
public class InputStreamUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";//编码
    private static final int PROTECTED_LENGTH = 51200;// 输入流保护 50KB

    public static String readInfoStream(InputStream input) throws Exception {
        if (input == null) {
            throw new Exception("输入流为null");
        }
        //字节数组
        byte[] bcache = new byte[2048];
        int readSize = 0;//每次读取的字节长度
        int totalSize = 0;//总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        try {
            //一次性读取2048字节
            while ((readSize = input.read(bcache)) > 0) {
                totalSize += readSize;
                if (totalSize > PROTECTED_LENGTH) {
                    throw new Exception("输入流超出50K大小限制");
                }
                //将bcache中读取的input数据写入infoStream
                infoStream.write(bcache, 0, readSize);
            }
        } catch (IOException e1) {
            throw new Exception("输入流读取异常");
        } finally {
            try {
                //输入流关闭
                input.close();
            } catch (IOException e) {
                throw new Exception("输入流关闭异常");
            }
        }

        try {
            String str = infoStream.toString(DEFAULT_ENCODING);
            return parsToJsonStr(str);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("输出异常");
        }
    }


    public static String parsToJsonStr(String s) throws UnsupportedEncodingException {
        JSONObject bodys = new JSONObject();
        for (String one : s.split("&")) {
            String[] oneEles = one.split("=");
            String key = java.net.URLDecoder.decode(oneEles[0], "UTF-8");
            String val = java.net.URLDecoder.decode(oneEles[1], "UTF-8");
            bodys.put(key, val);
        }
        return bodys.toJSONString();
    }
}
