package com.unam.ciencias.modelado.cloudcoverage;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Image {

    private BufferedImage image;

    private void Image(){}

    public void Image(String imageFileName) {
        try {
            image = ImageIO.read(ImageIO.createImageInputStream
                (new FileInputStream(imageFileName)));
        } catch (FileNotFoundException e) {
            //Llenar
        } catch (IOException i) {
            //Llenar
        }
    }

    public int colorPixelCount(Color color){
        return 0;
    }

    public void toBlackendWhite(){

    }

}