package com.liumapp.qtools.pic;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * file AsciiPicTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/19
 */
public final class AsciiPicTool {

    public static String pic2DefaultAscii (File sourcePic) throws IOException {
        return AsciiPicTool.pic2Ascii(sourcePic, 0.25);
    }

    public static String pic2SmallAscii (File sourcePic) throws IOException {
        return AsciiPicTool.pic2Ascii(sourcePic, 0.10);
    }

    public static String pic2Ascii(File sourcePic, double scale) throws IOException {
        final String BaseCharacter = "M&$B%0eol1v!'=+;:.";// "#&$B%eaovl+;:. ";//"B&eavoL!,. ";//" //
        String result = "";
        // #&$%*o!;.";//"M&Dn%l+-,. "//MN&$Beon%vl+;:.
        try {
            final BufferedImage image = ImageIO.read(sourcePic);
            int heightOfImg = (int) Math.ceil(image.getHeight());
            int widthOfImg = (int) Math.ceil(image.getWidth());

            for (int y = 0; y < heightOfImg; y += (2 / scale)) {
                for (int x = 0; x < widthOfImg; x += 1 / scale) {
                    final int pixel = image.getRGB(x, y);
                    final int a = (pixel >> 24) & 0xff;// alpha通道，0为全透明
                    String resultCharacters;
                    if (a > 10) {
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = pixel & 0xff;
                        float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                        int indexOfBaseCharacter = Math.round(gray * (BaseCharacter.length() + 1) / 255);
                        resultCharacters = indexOfBaseCharacter >= BaseCharacter.length() ? " "
                                : String.valueOf(BaseCharacter.charAt(indexOfBaseCharacter));
                    } else {
                        resultCharacters = " ";// 透明部分用空格填充
                    }
                    result += resultCharacters;
                }
                result += "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
