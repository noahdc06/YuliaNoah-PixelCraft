import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
//Jigsaw breaks off nine sections of an image and swaps each section with the one on the opposite side
public class jigsaw extends BaseConverter {
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
    	BufferedImage inputImage = readImage(inputFileName);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int sectWidth = width / 3;
        int sectHeight = height / 3;
        //The image is broken off into nine sections, and replaced in each of those sections
        for (int sect = 0; sect < 9; sect++) {
        	//Getting starting coordinates of the section and plotting where they will go
            int ogCol = sect % 3;
            int ogRow = sect / 3;
            int newSect = 8 - sect;
            int newCol = newSect % 3;
            int newRow = newSect / 3;
            int ogXStart = ogCol * sectWidth;
            int ogYStart = ogRow * sectHeight;
            int newXStart = newCol * sectWidth;
            int newYStart = newRow * sectHeight;

            // This code calculates height and width adjusting if pixels dont split evenly
            int xWidth;
            if (ogCol == 2) {
                xWidth = width - 2 * sectWidth;
            } else {
                xWidth = sectWidth;
            }
            int yHeight;
            if (ogRow == 2) {
                yHeight = height - 2 * sectHeight;
            } else {
                yHeight = sectHeight;
            }

            // Copies the pixels into the final image
            for (int y = 0; y < yHeight; y++) {
                for (int x = 0; x < xWidth; x++) {
                    int pixel = inputImage.getRGB(ogXStart + x, ogYStart + y);
                    outputImage.setRGB(newXStart + x, newYStart + y, pixel);
                }
            }
        }

        writeImage(outputImage, outputFileName);
    }
}
