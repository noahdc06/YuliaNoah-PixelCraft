import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pixelate extends BaseConverter {
    private static final int groupsize = 15; // Will cover a set of 15x15 pixels
    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private int width, height;

    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        inputImage = readImage(inputFileName);
        width = inputImage.getWidth();
        height = inputImage.getHeight();
        outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        processBlock(0, 0); // Start recursive processing
        writeImage(outputImage, outputFileName);
    }

    private void processBlock(int x, int y) {
        // Base case: entire image processed
        if (y >= height) return;

        // Process current block
        int endX = Math.min(x + groupsize, width);
        int endY = Math.min(y + groupsize, height);

        // Calculate average color for this block
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        int count = 0;

        for (int row = y; row < endY; row++) {
            for (int col = x; col < endX; col++) {
                ARGB pixel = new ARGB(inputImage.getRGB(col, row));
                totalRed += pixel.red;
                totalGreen += pixel.green;
                totalBlue += pixel.blue;
                count++;
            }
        }

        ARGB avgColor = new ARGB(255, totalRed / count, totalGreen / count, totalBlue / count);

        // Fill the block with the average color
        for (int row = y; row < endY; row++) {
            for (int col = x; col < endX; col++) {
                outputImage.setRGB(col, row, avgColor.toInt());
            }
        }

        // Recursively process the next block
        if (x + groupsize >= width) {
            processBlock(0, y + groupsize);
        } else {
            processBlock(x + groupsize, y);
        }
    }
}
