import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Rotate extends BaseConverter {
    public void convert(String inputFileName, String outputFileName) throws IOException {
    	// Uses BaseConverter method to get the input image
    	BufferedImage inputImage = readImage(inputFileName);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // Creates a new image frame with swapped height and width values
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        
        // Loop to iterate through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	// Outputs the pixel to its swapped coordinate in the new frame
                int n = inputImage.getRGB(x, y);
                outputImage.setRGB(height - 1 - y, x, n);
            }
        }

      // Uses BaseConverter method to output the image to PixelCraft
        writeImage(outputImage, outputFileName);
    }
}
