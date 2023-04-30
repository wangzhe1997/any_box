package com.wp.anybox.utils;

import org.apache.shiro.codec.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String doGet(String urlStr, String headers) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();


            // 设置请求方法为 GET
            con.setRequestMethod("GET");

            // 设置请求头部信息
            if (headers != null && !"".equals(headers)) {
                String[] headerList = headers.split(";");
                for (String header : headerList) {
                    String[] headerPair = header.split(":");
                    con.setRequestProperty(headerPair[0].trim(), headerPair[1].trim());
                }
            }

            // 发送 GET 请求
            int responseCode = con.getResponseCode();


            if (HttpURLConnection.HTTP_OK == responseCode) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
            } else {
                sb.append("Response Code: ").append(responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static String getImgBase64(String imgUrl) {
        // 创建 HttpURLConnection 对象
        HttpURLConnection conn = null;
        try {
            URL url = new URL(imgUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            // 获取响应状态码，200 表示成功
            if (conn.getResponseCode() == 200) {
                // 获取响应流
                InputStream inputStream = conn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                // 将响应流写入字节数组输出流
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                byte[] bytes = outStream.toByteArray();
                // 关闭流
                outStream.close();
                inputStream.close();
                conn.disconnect();
                // 转成 Base64
                return Base64.encodeToString(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String doPost(String urlStr, String body, String headers, String contentType) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 POST
            con.setRequestMethod("POST");

            // 设置请求头部信息
            if (headers != null && !"".equals(headers)) {
                String[] headerList = headers.split(";");
                for (String header : headerList) {
                    String[] headerPair = header.split(":");
                    con.setRequestProperty(headerPair[0].trim(), headerPair[1].trim());
                }
            }

            // 设置 Content-Type
            if (contentType != null && !"".equals(contentType)) {
                con.setRequestProperty("Content-Type", contentType);
            }

            // 发送 POST 请求
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
            } else {
                sb.append("Response Code: ").append(responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
