package com.unam.ciencias.modelado.cloudcoverage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Class to deal with circular images; the image is meant to be contained in a
 * rectangle. The image inside the circle is considered the image (or area) of
 * interest. The rectangle is considered the total image (or area).
 */
public class CircularImage {

    // An accesible buffer of the image data.
    private BufferedImage image;

    // The radio of the circle that cointains the image of interest.
    private int radio;

    // The x coordinate of the circle that contains the image of interest.
    private int centerX;

    // The y coordinate of the circle that contains the image of interest.
    private int centerY;

    //The Matrix of Colors when the image is converted to black and white.
    private Color[][] binarized;

    //The name of the File.
    private String imageFileName;
    
    /**
     * Private constructor with no arguments, so to make necessary the other
     * constructors.
     */
    private CircularImage() {}

    /**
     * Constructs a CircularImage with the image on imageFileName. The parameters
     * are set as the class variables.
     * 
     * @param imageFileName the name of the file containing the image.
     * @param radio         the radio of the circle that contains the image of
     *                      interest.
     * @param centerX       the x coordinate of the circle that contains the image
     *                      of interest.
     * @param centerY       the y coordinate of the circle that contains the image
     *                      of interest.
     */
    public CircularImage(String imageFileName, int radio, int centerX, int centerY)
            throws IOException, FileNotFoundException {
        image = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream(imageFileName)));
        // If the File is not an Image or the size is incorrect throws an IOException
        if (image == null || image.getWidth() != 4368 || image.getHeight() != 2912)
            throw new IOException();
        this.imageFileName = imageFileName;
        this.radio = radio;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Constructs a CircularImage with the image on imageFileName. As the center and
     * radio are not given, the center of the circle is considered to be the center
     * of the rectangle, and the radio is the longitude of the shortest way from the
     * center to a side of the image.
     * 
     * @param imageFileName the name of the file containing the image.
     * @throws IOException           if an input or output exception occured.
     * @throws FileNotFoundException if the file imageFileName is't found.
     */
    public CircularImage(String imageFileName) throws IOException, FileNotFoundException {
        image = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream(imageFileName)));
        // If the File is not an Image or the size is incorrect throws an IOException
        if (image == null || image.getWidth() != 4368 || image.getHeight() != 2912)
            throw new IOException();
        this.imageFileName = imageFileName;
        this.centerX = image.getWidth() / 2;
        this.centerY = image.getHeight() / 2;
        this.radio = (centerX < centerY) ? centerX : centerY;
    }

    /**
     * Calculates the number of pixels in the total image.
     * 
     * @return the area (in pixels) of the total image.
     */
    public int getRectangleArea() {
        return image.getHeight() * image.getWidth();
    }

    /**
     * Estimates the number of pixels inside the circle.
     * 
     * @return an estimation of the area (in pixels) inside the circle.
     */
    public int getCircleArea() {
        return (int) (Math.PI * radio * radio);
    }

    /**
     * Estimates the distance between, a point with coordinates (x, y) inside the
     * total image, and the center of the circle. The units of this distance will be
     * pixels.
     * 
     * @param x the coordinate in x of the point.
     * @param y the coordinate in y of the point.
     * @return an estimation of the distance between a point with coordinates (x, y)
     *         and the center of the circle.
     */
    private float centerDistance(int x, int y) {
        int xDistance = Math.abs(x - centerX);
        int yDistance = Math.abs(y - centerY);
        return (float) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    /**
     * Calculates the proportion of red out of blue in the given color. If the given
     * color lacks of blue value, then the function returns the red value plus 1.
     * Note: the default RGB color model represent each color value as an integer
     * number, so there cannot be blue or red values between 0 and 1; when the blue
     * value equals 0, the output of this function is the red value plus 1, which is
     * the biggest proportion possible.
     * 
     * @param color an integer that represents a color, using default RGB color
     *              model (TYPE_INT_ARGB)and default sRGB colorspace.
     * @return if the blue value of the color is 0, then the red value; if the blue
     *         value of the color isn't 0 then the quotient of the red value out of
     *         the blue value.
     */
    private float getRedBlueProportion(int color) {
        float redValue = (color >> 16) & 0xFF;
        float blueValue = (color) & 0xFF;
        if (blueValue == 0)
            return redValue + 1;
        return redValue / blueValue;
    }

    /**
     * Counts the number of pixels within the area of interest that have a red/blue
     * proportion less than the given treshold.
     * 
     * @param treshold the treshlod that determines wich pixels are counted.
     * @return the number of pixels which red/blue proportion is less than the
     *         treshold.
     */
    public int redBlueProportionPixelCount(float treshold) {
        int colorPixels = 0;
        int r = radio * radio;
        int actualPixel;
        int xDistance;
        int yDistance;
        binarized = new Color[image.getWidth()][image.getHeight()];
        // Loop over all the pixels in the total image.
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                // First we check if the pixel is inside the circle.
                xDistance = i - centerX;
                yDistance = j - centerY;
                if ((xDistance * xDistance) + (yDistance * yDistance) >= r) {
                    // If the pixel is not inside the circle we put the gray color in binarized and we ignore it.
                    binarized[i][j] = Color.DARK_GRAY.darker();
                    continue;
                }
                actualPixel = image.getRGB(i, j);
                //If the pixel is Sky
                if (getRedBlueProportion(actualPixel) < treshold) {
                    binarized[i][j] = Color.WHITE;
                    colorPixels++;
                } else { //If the pixel is Cloud.
                    binarized[i][j] = Color.BLACK;
                }             
            }
        }
        return colorPixels;
    }

    /**
     * Method to create a BufferedImage with the binarized matrix
     * @return BufferedImage with the Black and White information.
     */
    private BufferedImage binarizedMatrixToBufferedImage() {
        BufferedImage buImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                buImage.setRGB(i, j, binarized[i][j].getRGB());
            }
        }
        return buImage;
    }

    /**
     * Creates an image equal to this, but only using black and white colors. The
     * image is saved into this floder.....
     */
    public void toBlackAndWhite() throws IOException {
        if (binarized == null)
            return;
    
        StringBuilder sb = new StringBuilder(imageFileName);
        sb.insert(imageFileName.length()-4, "-seg");
        String blackWhiteFilename = sb.toString();

        BufferedImage blackWhite = binarizedMatrixToBufferedImage();
        ImageIO.write(blackWhite, "jpg", new File(blackWhiteFilename));
    }
}