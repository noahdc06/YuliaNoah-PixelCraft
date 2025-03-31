import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/*This program aims to remove the edges of an image by creating a new, smaller image frame
 * and inputting into it pixels that appear only within its bounds. */
public class Crop extends BaseConverter {
	public void convert(String inputFileName, String outputFileName) throws IOException {
		// Uses BaseConverter method to get the input image
		BufferedImage inputImage = readImage(inputFileName);
		int originalWidth = inputImage.getWidth();
		int originalHeight = inputImage.getHeight();
		
		// Sets parameters for the cropped image
		int startX = originalWidth/4;
		int startY = originalHeight/4;
		int cropWidth = originalWidth/2;
		int cropHeight = originalHeight/2;
		
		//Creates the frame for the cropped image
		BufferedImage outputImage = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_RGB);
		
		// Re-generates identical pixels only within the cropped area
		for(int y=0; y<cropHeight; y++) {
			for(int x=0; x<cropWidth; x++) {
				int pixel = inputImage.getRGB(startX+x,  startY+y);
				outputImage.setRGB(x, y, pixel);
			}
		}
		// Uses BaseConverter method to output the image to PixelCraft
		writeImage(outputImage, outputFileName);
	}
	

}
