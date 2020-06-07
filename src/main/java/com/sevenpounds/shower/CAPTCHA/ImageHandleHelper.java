package com.sevenpounds.shower.CAPTCHA;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;

public class ImageHandleHelper {
    public ImageHandleHelper() {
    }

    public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width, int hight) throws Exception {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)readers.next();
        InputStream source = new FileInputStream(srcFile);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
    }

    public static void main(String[] args) {
        String[] files = new String[]{"C:/图片/a.jpg", "C:/图片/b.jpg", "C:/图片/c.jpg", "C:/图片/d.jpg", "C:/图片/e.jpg"};
        BufferedImage[] images = getBuff(files);
        byte[] bytes = getImageArray(images, "交通工具");
        byte2image(bytes, "C:/图片/z.jpg");
    }

    public static BufferedImage[] getBuff(String[] files) {
        int len = files.length;
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];

        for(int i = 0; i < len; ++i) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
        }

        return images;
    }

    public byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;

        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            boolean var6 = false;

            int numBytesRead;
            while((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }

            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return data;
    }

    public static void byte2image(byte[] data, String path) {
        if (data.length >= 3 && !path.equals("")) {
            try {
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
                imageOutput.write(data, 0, data.length);
                imageOutput.close();
                System.out.println("Make Picture success,Please find image in " + path);
            } catch (Exception var3) {
                System.out.println("Exception: " + var3);
                var3.printStackTrace();
            }

        }
    }

    public static byte[] getImageArray(BufferedImage[] images, String str) {
        BufferedImage buff1 = mergeImage(images, 1);
        BufferedImage buff2 = productImage(str);
        images = new BufferedImage[]{buff2, buff1};
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(mergeImage(images, 2), "jpg", out);
        } catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }

        return out.toByteArray();
    }

    public static BufferedImage productImage(String s) {
        int width = 190;
        int height = 38;
        s = "请点击属于" + s + "的所有汉字";
        Font font = new Font("Arial Black", 1, 10);
        BufferedImage bi = new BufferedImage(width, height, 1);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.RED);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = ((double)width - bounds.getWidth()) / 2.0D;
        double y = ((double)height - bounds.getHeight()) / 2.0D;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        g2.drawString(s, (int)x, (int)baseY);
        return bi;
    }

    public static BufferedImage mergeImage(BufferedImage[] images, int type) {
        int len = images.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        } else {
            int[][] ImageArrays = new int[len][];

            int newHeight;
            int newWidth;
            int i;
            for(newHeight = 0; newHeight < len; ++newHeight) {
                newWidth = images[newHeight].getWidth();
                i = images[newHeight].getHeight();
                ImageArrays[newHeight] = new int[newWidth * i];
                ImageArrays[newHeight] = images[newHeight].getRGB(0, 0, newWidth, i, ImageArrays[newHeight], 0, newWidth);
            }

            newHeight = 0;
            newWidth = 0;

            for(i = 0; i < images.length; ++i) {
                if (type == 1) {
                    newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                    newWidth += images[i].getWidth();
                } else if (type == 2) {
                    newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                    newHeight += images[i].getHeight();
                }
            }

            if (type == 1 && newWidth < 1) {
                return null;
            } else if (type == 2 && newHeight < 1) {
                return null;
            } else {
                try {
                    BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, 1);
                    int height_i = 0;
                    int width_i = 0;

                    for(int j = 0; j < images.length; ++j) {
                        if (type == 1) {
                            ImageNew.setRGB(width_i, 0, images[j].getWidth(), newHeight, ImageArrays[j], 0, images[j].getWidth());
                            width_i += images[j].getWidth();
                        } else if (type == 2) {
                            ImageNew.setRGB(0, height_i, newWidth, images[j].getHeight(), ImageArrays[j], 0, newWidth);
                            height_i += images[j].getHeight();
                        }
                    }

                    return ImageNew;
                } catch (Exception var10) {
                    throw new RuntimeException(var10);
                }
            }
        }
    }

    public static final void overlapImage(String bigPath, String smallPath, String outFile) {
        try {
            BufferedImage big = ImageIO.read(new File(bigPath));
            BufferedImage small = ImageIO.read(new File(smallPath));
            Graphics2D g = big.createGraphics();
            int x = (big.getWidth() - small.getWidth()) / 2;
            int y = (big.getHeight() - small.getHeight()) / 2;
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), (ImageObserver)null);
            g.dispose();
            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }
}