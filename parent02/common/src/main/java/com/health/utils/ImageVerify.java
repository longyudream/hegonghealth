package com.health.utils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;


public class ImageVerify {

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getLetterSpace() {
        return letterSpace;
    }

    public void setLetterSpace(int letterSpace) {
        this.letterSpace = letterSpace;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public static class ImageCode {
        private BufferedImage image;
        private String code;
        private LocalDateTime expireTime;

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public LocalDateTime getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(LocalDateTime expireTime) {
            this.expireTime = expireTime;
        }

        public ImageCode(final BufferedImage image, final String code, final int expireIn) {
            this.image = image;
            this.code = code;
            this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        }

        public boolean isExpire() {
            return LocalDateTime.now().isAfter(expireTime);
        }
    }

    private int width = 100;                  // 验证码图片宽度
    private int height = 36;                  // 验证码图片长度
    private int length = 4;                   // 验证码位数
    private int fontSize = 32;                // 验证码字体大小
    private int letterSpace = fontSize / 2;   // 字母间隔
    private int lineSize = 200;               // 干扰线数量
    private int expireIn = 60;                // 验证码有效时间 60s
    private int positionX = 18;               // x轴距
    private int positionY = 28;               // y轴距

    public ImageVerify() {
    }

    public ImageVerify(final int expireIn) {
        this.expireIn = expireIn;
    }

    public ImageVerify(final int width, final int height, final int length, final int fontSize, final int expireIn, final int positionX, final int positionY) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.fontSize = fontSize;
        this.expireIn = expireIn;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public ImageCode create() {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics graphics = image.getGraphics();
        graphics.setColor(getRandColor(200, 500));
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("Times New Roman", Font.ITALIC, fontSize));
        graphics.setColor(getRandColor(100, 200));

        final Random random = new Random();

        /** 画干扰线*/
        for (int i = 0; i < lineSize; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        /** 画验证码*/
        final StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            final String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            graphics.setColor(
                    new Color(
                            20 + random.nextInt(110),
                            20 + random.nextInt(110),
                            20 + random.nextInt(110)
                    )
            );
            graphics.drawString(rand, letterSpace * i + positionX, positionY);
        }
        graphics.dispose();

        return new ImageCode(image, sRand.toString(), expireIn);
    }

    private Color getRandColor(int fc, int bc) {
        final Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
