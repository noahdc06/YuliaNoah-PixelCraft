
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class crop implements Converter {
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		
		BufferedImage inputImage = ImageIO.read(new File(inputFileName));
		int originalWidth = inputImage.getWidth();
		int originalHeight = inputImage.getHeight();
		
		// portrait crop 
		int startX = originalWidth/4;
		int startY = originalHeight/4;
		int cropWidth = originalWidth/2;
		int cropHeight = originalHeight/2;
		
		if(startX<0 || startY<0 || cropWidth<=0 || cropHeight<=0 || startX + cropWidth > originalWidth || startY+cropHeight > originalHeight) {
			throw new IOException();
		}
		
		BufferedImage outputImage = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_RGB);
		
		for(int y=0; y<cropHeight; y++) {
			for(int x=0; x<cropWidth; x++) {
				int pixel = inputImage.getRGB(startX+x,  startY+y);
				outputImage.setRGB(x, y, pixel);
			}
		}
		
		ImageIO.write(outputImage,  "png", new File(outputFileName));
	}
	

}
