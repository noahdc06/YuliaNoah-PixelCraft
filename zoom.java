
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class zoom extends Converter{
	
	@Override
	public void convert(String inputFileName, String outputFileName) throws IOException {
		
		BufferedImage inputImage = readImage(inputFileName);
		int originalWidth = inputImage.getWidth();
		int originalHeight = inputImage.getHeight();
		
		// Adjust zoom amount
		double zoomAmt = 2.0;
		
		int newWidth = (int)(originalWidth/zoomAmt);
		int newHeight = (int)(originalHeight/zoomAmt);
		
		int startX = (originalWidth-newWidth) / 2;
		int startY = (originalHeight-newHeight) / 2;
		
		BufferedImage outputImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
		
		for(int y=0; y<originalHeight; y++) {
			for(int x=0; x<originalWidth; x++) {
				int originalX = (int)(startX+ (x/zoomAmt));
				int originalY = (int)(startY+ (y/zoomAmt));
				
				originalX = Math.max(0,  Math.min(originalX, originalWidth-1));
				originalY = Math.max(0, Math.min(originalY,  originalHeight-1));
				
				int pixel = inputImage.getRGB(originalX, originalY);
				outputImage.setRGB(x,  y,  pixel);
			}
		}
		
		writeImage(outputImage, outputFileName);
		
	}

}
