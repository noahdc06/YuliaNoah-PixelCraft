
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Vignette extends BaseConverter{
	
	private static final int MAX_DARKNESS = 128;
	private BufferedImage inputImage;
	private BufferedImage outputImage;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	
	@Override
	public void convert(String inputFileName, String outputFileName) throws IOException {
		
		inputImage = readImage(inputFileName);
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		centerX = width/2;
		centerY = height/2;
		
		// Iterative processing of pixels, row by row
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				
				// Calculating the amount of darkness we have to apply based on how far from the center the pixel is located
				double distance = Math.sqrt(Math.pow((x-centerX)/(double) centerX, 2) + Math.pow((y-centerY)/(double) centerY, 2));
				distance = Math.min(1.0, distance);
				int darkenAmt = (int)(distance*MAX_DARKNESS);
				
				// Getting the original RGB value, then making it appropriately darker
				ARGB pixel = new ARGB(inputImage.getRGB(x, y));
				ARGB darkenedPixel = new ARGB(255, Math.max(0,  pixel.red-darkenAmt), Math.max(0,  pixel.green-darkenAmt), Math.max(0,  pixel.blue-darkenAmt));
				
				outputImage.setRGB(x, y, darkenedPixel.toInt());
			}
		}
		
		writeImage(outputImage, outputFileName);
		
	}
	
}
