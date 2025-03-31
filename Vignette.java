import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This code aims to create a dark 'vignette' effect around an image
 * by darkening the pixels of the image based on how far from the
 * center they are.*/
public class Vignette extends BaseConverter {
    public void convert(String inputFileName, String outputFileName) throws IOException {
        // Uses BaseConverter method to get the input image
        BufferedImage inputImage = readImage(inputFileName);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // Creates the frame for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int centerX = width / 2;
        int centerY = height / 2;
        
        // Loop to iterate through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Calculates the amount of darkness we have to apply based on how far from the center the pixel is
                double distance = Math.sqrt(Math.pow((x - centerX) / (double) centerX, 2) + Math.pow((y - centerY) / (double) centerY, 2));
                distance = Math.min(1.0, distance);
                // Maximum darkness is a set value
                int darkenAmt = (int) (distance * 128);
                
                // Replaces the original pixel with a new, darker pixel
                ARGB pixel = new ARGB(inputImage.getRGB(x, y));
                ARGB darkenedPixel = new ARGB(255, Math.max(0, pixel.red - darkenAmt), Math.max(0, pixel.green - darkenAmt), Math.max(0, pixel.blue - darkenAmt));
                outputImage.setRGB(x, y, darkenedPixel.toInt());
            }
        }
        
        // Uses BaseConverter method to output the image to PixelCraft
        writeImage(outputImage, outputFileName);
    }
}
