package com.unam.ciencias.modelado.cloudcoverage;

/**
 * Main class of the program.
 * The objective of this program is to calculate the cloud coverage index of
 * pictures of the sky, this pictures are taken with a 360 degree lens.
 * The input images will be images with a center in the pixel (2184, 1456) and
 * a radio of 1324 pixels.
 */
public class Main {
    /**
     * Main method of the program, is used to run the program.
     * @param args The arguments of the program such as the image.jpg or the flag S or s.
     */
    public static void main(String[] args) {
        Run cloudCoverage = new Run();
        cloudCoverage.run(args);
    }
}
