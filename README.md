Cloud Coverage.
====================
### Authors 
- Carlos Andrade Hernández
- Demian Alejandro Monterrubio Acosta
### Context
There is an atmospheric registration station where photographs are taken every 15 minutes
from the sky with a 360 degree wide angle lens. The images of the celestial vault are
stored in jpeg format image files. In each of these photographs it must be
calculate the cloud coverage index (CCI),
that is, the proportion of the sky covered by clouds. For the purpose of this
project, the CCI will be considered as the quotient of the number of pixels that are classified as
cloud over the total number of pixels in the image's area of interest.

### Input
The program input must be the name of a 4,368 jpeg image file
pixels wide and 2,912 pixels high (center in column 2184 and row 1456) of
about 32MB in size. The image of the sky is a circle (approximate radius 1324
pixels) contained in the image rectangle. The center of the circle and the center of the rectangle
contains match. Each pixel in the image has three color components R (red), G (green)
and B (blue). The file name must be given to the program through the call line.
The program must also receive, as an optional input parameter, an "S" flag
uppercase or lowercase that will indicate whether a blank file should be output and
black with white clouds and black sky.

### Output
The cloud cover index should be displayed on the standard output. If the circle in the
center of the image has a total area of C pixels and within it are N pixels that were
classified as "cloud", then the cloud cover index is defined as the quotient:

### CCI = C / N

If the program is invoked with the "S" flag then an image must also be generated
in jpg or png format in black and white with the "clouds" in white and the "sky" in black. The
The name of the output image file must match that of the input file, except for
that, before the extension, the segmentation suffix “-seg” must be added. In the examples of
above, the file 11773-seg.jpg or 11773-seg.png must be generated.

### To run this App:
Maven Website : https://maven.apache.org/
You must have Maven installed : https://maven.apache.org/download.cgi

```
$ mvn clean
$ mvn compile
$ mvn install  
$ java -jar target/CloudCoverage.jar image.jpg S
$ java -jar target/CloudCoverage.jar image.jpg s
$ java -jar target/CloudCoverage.jar image.jpg 
```
### OutFile
The output file will be saved in this folder.