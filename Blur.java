import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This program aims to create a 'blur' effect on an image by changing the ARGB of each pixel
 * to the average ARGB of every pixel within 2 pixels of that pixel.*/
public class Blur extends BaseConverter {
	public void convert(String inputFileName, String outputFileName) throws IOException {
		// Uses BaseConverter method to get the input image
		BufferedImage inputImage = readImage(inputFileName);
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		// Creates the frame for the output image
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//Loop to iterate through every pixel
		for(int y = 0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int totalRed = 0;
				int totalGreen = 0;
				int totalBlue = 0;
				int count = 0;
				
				// Checks the ARGB of every pixel within 2 pixels of this one
				for(int yShift = -2; yShift <= 2; yShift++) {
					for(int xShift = -2;xShift<=2;xShift++) {
						int xColor = x + xShift;
						int yColor = y + yShift;
						// Checks if the pixel is within bounds
						if(xColor >= 0 && xColor < width && yColor>=0 && yColor<height) {
							// Adds together all the ARGB values
							ARGB pixel = new ARGB(inputImage.getRGB(xColor,  yColor));
							totalRed += pixel.red;
							totalGreen += pixel.green;
							totalBlue += pixel.blue;
							count++;
						}
					}
				}
				
				// Sets the ARGB of this pixel to the average value
				ARGB blurredPixel = new ARGB(255, totalRed/count, totalGreen/count, totalBlue/count);
				outputImage.setRGB(x, y, blurredPixel.toInt());
			}
		}
		//Uses BaseConverter method to output the image to PixelCraft
		writeImage(outputImage, outputFileName);
	}
}
