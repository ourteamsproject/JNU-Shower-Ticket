package com.sevenpounds.shower.util;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

public class ImageUtils {
    public ImageUtils() {
    }

    public static String getImageStr(String imgFile) {
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static byte[] getImageByte(String imgFile) {
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return data;
    }

    public static boolean generateImage(String imgStr, String imgFile) throws Exception {
        if (imgStr == null) {
            return false;
        } else {
            try {
                OutputStream out = new FileOutputStream(imgFile);
                out.write(imgStr.getBytes());
                out.flush();
                out.close();
                return true;
            } catch (Exception var4) {
                throw var4;
            }
        }
    }

    public static boolean isRightFormat(String format) {
        return format.equals("jpg") || format.equals("gif") || format.equals("png");
    }

    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {
        int width = originalImage.getWidth() * times;
        int height = originalImage.getHeight() * times;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver)null);
        g.dispose();
        return newImage;
    }

    public static boolean zoomInImage(String srcPath, String newPath, Integer times) {
        BufferedImage bufferedImage = null;

        try {
            File of = new File(srcPath);
            if (of.canRead()) {
                bufferedImage = ImageIO.read(of);
            }
        } catch (Exception var6) {
            return false;
        }

        if (bufferedImage != null) {
            bufferedImage = zoomInImage(bufferedImage, times);

            try {
                ImageIO.write(bufferedImage, "JPG", new File(newPath));
            } catch (IOException var5) {
                return false;
            }
        }

        return true;
    }

    public static BufferedImage zoomOutImage(BufferedImage originalImage, Integer times) {
        int width = originalImage.getWidth() / times;
        int height = originalImage.getHeight() / times;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver)null);
        g.dispose();
        return newImage;
    }

    public static boolean zoomOutImage(String srcPath, String newPath, Integer times) {
        BufferedImage bufferedImage = null;

        try {
            File of = new File(srcPath);
            if (of.canRead()) {
                bufferedImage = ImageIO.read(of);
            }
        } catch (IOException var6) {
            return false;
        }

        if (bufferedImage != null) {
            bufferedImage = zoomOutImage(bufferedImage, times);

            try {
                ImageIO.write(bufferedImage, "JPG", new File(newPath));
            } catch (IOException var5) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        boolean testIn = zoomInImage("E:/11.jpg", "E:\\in.jpg", 4);
        if (testIn) {
            System.out.println("in ok");
        }

        boolean testOut = zoomOutImage("E:/11.jpg", "E:\\out.jpg", 4);
        if (testOut) {
            System.out.println("out ok");
        }

    }
}
