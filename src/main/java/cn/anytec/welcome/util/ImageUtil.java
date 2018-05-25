package cn.anytec.welcome.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtil {

    public static byte[] cutImg(byte[] pic, String format,int x, int y, int width, int height) {

        ByteArrayInputStream in = null;
        BufferedImage imageHandle = null;
        try {

            in = new ByteArrayInputStream(pic);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(format);
            ImageReader reader = readers.next();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(in);
            reader.setInput(imageInputStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            if (x < 0) {
                width = width + x;
                x = 0;
            }
            if (y < 0) {
                height = height + y;
                y = 0;
            }
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            imageHandle = reader.read(0, param);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bufferedImageToBytes(imageHandle,format);
    }


    public static byte[] bufferedImageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }


}
