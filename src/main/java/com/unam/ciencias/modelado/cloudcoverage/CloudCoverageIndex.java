package com.unam.ciencias.modelado.cloudcoverage;

public class CloudCoverageIndex {

    public static float getIndex(float cloudArea, float totalArea) {
        if (totalArea <= 0)
            return 0;
        return cloudArea / totalArea;
    }
}