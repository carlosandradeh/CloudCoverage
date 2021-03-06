package com.unam.ciencias.modelado.cloudcoverage;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Class to execute the program.
 * This class is to get the input parameters, administrate exceptions and execute
 * the needed methods.
 */
public class Run {
    
    /**
     * Method to show how this program must be used.
     */
    private static void use() {
        System.err.println("Use: java -jar target/CloudCoverage.jar img.jpg S");
        System.out.println("The input image must be size 4368px*2912px");
        System.exit(1);
    }

    /**
     * Method to create a new Instance of Circular Image. Throws File not Found
     * Exception if the image is not found or IOException if it's an invalid input.
     * @param image The path of the image
     * @param radio The radio of the circle in the image.
     * @param x     The position x of the center of the image.
     * @param y     The position y of the center of the image.
     * @return
     */
    private CircularImage createCircularImage(String image, int radio, int x, int y) {
        try {
            return new CircularImage(image, radio, x, y);
        } catch (FileNotFoundException e) {
            System.err.printf("File %s not found :-( \n", image);
            use();
        } catch (IOException io) {
            System.err.printf("File %s not valid :-( \n", image);
            use();
        }
        return null;
    }

    /**
     * Method to create a new Black and White Image from a CircularImage
     * @param c CircularImage
     */
    private void createBlackWhiteImage(CircularImage c) {
        try {
            c.toBlackAndWhite();
            System.out.println("Black And White Image created successfully in this folder! :-)");
        } catch (IOException e) {
            System.err.println("Black and White Image creation Error :-( \n");
            System.exit(1);
        }
    }

    /**
     * Method to run the Cloud Coverage program.
     * Since the pictures are taken with a 360 dregree lens, the actual image
     * is inside of a rectangle with black borders; so we create a Circular Image
     * to manipulate the actual part that contains the sky.
     * @param args The arguments that will recive this program to work.
     */
    public void run(String[] args) {
        // If the input length is invalid.
        if (args.length == 0 || args.length >= 3) {
            System.err.printf("Invalid input \n");
            use();
        }

        //If the flag is invalid.
        if (args.length == 2 && !args[1].equals("s") && !args[1].equals("S")) {
            System.err.printf("Invalid flag %s \n", args[1]);
            use();
        }

        //The radio of the actual image of the sky.
        int radio = 1324;
        //The center in x coordinate of the actual image of the sky.
        int x = 2184;
        //The center in y coordinate of the actual image of the sky.
        int y = 1456;
        //Creation of a CircularImage object to manipulate correctly the images.
        CircularImage inputImage = createCircularImage(args[0], radio, x, y);

        //If the size of the image is invalid
        if (inputImage.getWidth() != 4368 || inputImage.getHeight() != 2912) {
            System.err.printf("Invalid size Image %s \n", args[0]);
            use();
        }

        // Calculate the Cloud Coverage Index of the image.
        float circleArea = (float)inputImage.getCircleArea();
        float cloudArea = circleArea - inputImage.redBlueProportionPixelCount(0.95f);
        float cloudCoverageIndex = CloudCoverageIndex.getIndex(cloudArea, circleArea);

        System.out.printf("The cloud Coverage index of the image %s is: %f \n", args[0], cloudCoverageIndex);

        // If we have a the flag S or s convert a copy of the image to black and white.
        if (args.length == 2)
            createBlackWhiteImage(inputImage);
    }
}