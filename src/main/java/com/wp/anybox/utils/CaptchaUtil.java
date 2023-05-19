package com.wp.anybox.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

public class CaptchaUtil {

    // 生成随机字符串方法（指定长度）
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62); // 62 是字符串 str 的总长度
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    // 生成验证码图片的方法
    public static BufferedImage generateCaptcha(String text, int width, int height) {
        BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) captchaImage.getGraphics();

        // 设置画布颜色为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 画出随机字符（干扰线和干扰点）
        Random random = new Random();
        g.setColor(Color.BLACK);
        Font font = new Font("微软雅黑", Font.PLAIN, 40);
        g.setFont(font);
        for (int i = 0; i < text.length(); i++) {
            int x = i * (width / text.length()) + random.nextInt(width / text.length() / 2);
            int y = height / 2 + random.nextInt(height / 3);
            g.drawString(String.valueOf(text.charAt(i)), x, y);
        }
        for (int i = 0; i < text.length(); i++) {
            int x1 = i * (width / text.length()) + random.nextInt(width / 5);
            int y1 = random.nextInt(height);
            int x2 = i * (width / text.length()) + width / text.length() + random.nextInt(width / 5);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.fillOval(x, y, 2, 2);
        }
        return captchaImage;
    }

    // 将验证码图片转化为 base64 字符串的方法
    public static String bufferedImageToBase64(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos); // 将 BufferedImage 写入到 ByteArrayOutputStream 中
            byte[] bytes = baos.toByteArray(); // 把原先 ByteArrayOutputStream 的数据转换成 byte 数组
            return Base64.getEncoder().encodeToString(bytes); // 字节数组转换为 base64 字符串
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close(); // 关闭流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 测试方法
    public static void main(String[] args) {
        String captchaText = getRandomString(6); // 生成 6 位随机验证码
        BufferedImage captchaImage = generateCaptcha(captchaText, 200, 50); // 生成验证码图片（200×50）
        String base64data = bufferedImageToBase64(captchaImage); // 将验证码图片转化为 base64 字符串
        System.out.println("captchaText = " + captchaText);
        System.out.println("base64data = " + base64data);
    }
}
