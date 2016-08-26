/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net3;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexandre
 */
public class ImageTest {
    public static void main(String[] args) throws IOException
    {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Alexandre\\Downloads\\index.png"));
        
        BufferedImage newimage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newimage.createGraphics();
        g.drawImage(image, 0, 0, 100, 100, null);
        
        File f = new File("C:\\Users\\Alexandre\\Downloads\\Nouvelle image.png");
        ImageIO.write(newimage, "PNG", f);
    }
}
