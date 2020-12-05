package com.unam.ciencias.modelado.cloudcoverage;

/**
 * Class to calculate the Cloud Coverage index.
 * The cloud coverage index is defined as the proportion od the Sky
 * covered by clouds.
 */
public class CloudCoverageIndex {

    /**
     * Method con Calculate the Cloud Covergae index.
     * @param cloudArea The total area of the clouds of a image. 
     * @param totalArea The total area of the circle of a image.
     * @return float index.
     */
    public static float getIndex(float cloudArea, float totalArea) {
        if (totalArea <= 0)
            return 0;
        return cloudArea / totalArea;
    }
}