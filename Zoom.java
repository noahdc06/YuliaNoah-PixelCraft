import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This program aims to create a new, 'zoomed' image using the same height and 
 * width of the original image but increasing the size of each pixel by double */
public class Zoom extends BaseConverter {
	public void convert(String inputFileName, String outputFileName) throws IOException {
		// Uses BaseConverter method to get the input image
		BufferedImage inputImage = readImage(inputFileName);
		int originalWidth = inputImage.getWidth();
		int originalHeight = inputImage.getHeight();
		
		//Sets parameters for zoomed image
		int newWidth = (int)(originalWidth/2.0);
		int newHeight = (int)(originalHeight/2.0);
		int startX = (originalWidth-newWidth) / 2;
		int startY = (originalHeight-newHeight) / 2;
		
		 // Creates the frame for the output image
		BufferedImage outputImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
		
		// Loop to iterate through each pixel
		for(int y=0; y<originalHeight; y++) {
			for(int x=0; x<originalWidth; x++) {
				//Calculates coordinates of the zoomed pixel
				int originalX = (int)(startX+ (x/2.0));
				int originalY = (int)(startY+ (y/2.0));
				// Ensures coordinates are within the image bounds
				originalX = Math.max(0,  Math.min(originalX, originalWidth-1));
				originalY = Math.max(0, Math.min(originalY,  originalHeight-1));
				// Replaces the pixel in the new image
				int pixel = inputImage.getRGB(originalX, originalY);
				outputImage.setRGB(x,  y,  pixel);
			}
		}
		
		// Uses BaseConverter method to output the image to PixelCraft
		writeImage(outputImage, outputFileName);
	}

}
