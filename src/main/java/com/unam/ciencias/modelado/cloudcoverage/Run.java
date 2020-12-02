package com.unam.ciencias.modelado.cloudcoverage;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Run {
    
    /**
     * Method to show how this program must be used.
     */
    private static void use() {
        System.err.println("Use: java -jar target/CloudCoverage.jar img.jpg S");
        System.exit(1);
    }

    /**
     * Method to create a new Instance of Circular Image. Throws File not Found
     * Exception if the image is not found or IOException if it's an invalid input
     * 
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
            System.err.printf("File %s not found :( \n", image);
            use();
        } catch (IOException io) {
            System.err.printf("File %s not valid :( \n", image);
            use();
        }
        return null;
    }

    /**
     * Method to run the Cloud Coverage program.
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

        CircularImage inputImage = createCircularImage(args[0], 1324, 2184, 1456);

        // Calculate the Cloud Coverage Index of the image.
        int circleArea = inputImage.getCircleArea();
        float cloudArea = circleArea - inputImage.redBlueProportionPixelCount(0.95f);
        float cloudCoverageIndex = CloudCoverageIndex.getIndex(cloudArea, circleArea);

        System.out.printf("The cloud Coverage index of the image %s is: %f \n", args[0], cloudCoverageIndex);

        // If we have a the flag S or s convert a copy of the image to black and white.
        if (args.length == 2)
            inputImage.toBlackAndWhite();
    }
}