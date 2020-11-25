package com.unam.ciencias.modelado.cloudcoverage;

public class CloudCoverageIndex {

    public static float getIndex(float cloudArea, float totalArea) {
        return cloudArea / totalArea;
    }

}