import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This class aims to create a 'gray' effect on the image by averaging out
 * the ARGB values on each pixel.*/
public class GrayScale extends BaseConverter {
    public void convert(String inputFileName, String outputFileName) throws IOException {
    	// Uses BaseConverter method to get the input image
        BufferedImage inputImage = readImage(inputFileName);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // Creates the frame for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // Loop to iterate through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	//Gets the average color of each ARGB value and sets them all to that average
                int pixel = inputImage.getRGB(x, y);
                ARGB argb = new ARGB(pixel);
                int average = (argb.red + argb.green + argb.blue) / 3;
                ARGB grayscaleArgb = new ARGB(argb.alpha, average, average, average);
                outputImage.setRGB(x, y, grayscaleArgb.toInt());
            }
        }
        // Uses BaseConverter method to output the image to PixelCraft
        writeImage(outputImage, outputFileName);
    }
}
