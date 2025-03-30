
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Vignette extends BaseConverter {
	
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
		
		// The recursion begins at the top left corner
		processPixel(0,0);
		
		writeImage(outputImage, outputFileName);
		
	}
	
	private void processPixel(int x, int y) {
		
		//Base case - stop when there are no more rows left to process
		if(y >= height) {
			return;
		}
		
		//Formula to calculate how much to darken each pixel based on distance from center x,y
		double distance = Math.sqrt(Math.pow((x - centerX)/(double) centerX, 2) + Math.pow((y-centerY)/(double) centerY, 2));
		distance = Math.min(1.0,  distance);
		int darkenAmt = (int)(distance*MAX_DARKNESS);
		
		ARGB pixel = new ARGB(inputImage.getRGB(x,  y));
		ARGB darkenedPixel = new ARGB(255, Math.max(0,  pixel.red-darkenAmt), Math.max(0, pixel.green-darkenAmt), Math.max(0, pixel.blue-darkenAmt));
		
		outputImage.setRGB(x, y, darkenedPixel.toInt());
		
		//Process next pixel 
		if(x+1 < width) {
			// In the next column
			processPixel(x+1, y);
		} else {
			// In the next row
			processPixel(0, y+1);
		}
		
	}
	
}
