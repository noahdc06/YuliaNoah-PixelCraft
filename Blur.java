
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class Blur extends Converter {
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		
		BufferedImage inputImage = readImage(inputFileName);
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		int blurRadius = 2;
		
		for(int y = 0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int totalRed = 0;
				int totalGreen = 0;
				int totalBlue = 0;
				int count = 0;
				
				for(int dy = -blurRadius; dy <= blurRadius; dy++) {
					for(int dx = -blurRadius;dx<=blurRadius;dx++) {
						int nx = x + dx;
						int ny = y + dy;
						
						if(nx >= 0 && nx < width && ny>=0 && ny<height) {
							ARGB pixel = new ARGB(inputImage.getRGB(nx,  ny));
							totalRed += pixel.red;
							totalGreen += pixel.green;
							totalBlue += pixel.blue;
							count++;
						}
					}
				}
				
				ARGB blurredPixel = new ARGB(255, totalRed/count, totalGreen/count, totalBlue/count);
				
				outputImage.setRGB(x, y, blurredPixel.toInt());
			}
		}
		
		writeImage(outputImage, outputFileName);
	}
	

}
