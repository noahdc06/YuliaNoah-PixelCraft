import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pixelate extends BaseConverter {
    private static final int groupsize = 15; // Covers a set of 15x15 pixels
    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private int width, height;

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

        // Calculate average color for this block using recursion
        int[] totals = calculateAverage(x, y, endX, endY, x, y, 0, 0, 0, 0);
        int avgRed = totals[0] / totals[3];
        int avgGreen = totals[1] / totals[3];
        int avgBlue = totals[2] / totals[3];
        ARGB avgColor = new ARGB(255, avgRed, avgGreen, avgBlue);

        // Fill the block with the average color using recursion
        setPixels(x, y, endX, endY, x, y, avgColor.toInt());

        // Recursively process the next block
        if (x + groupsize >= width) {
            processBlock(0, y + groupsize);
        } else {
            processBlock(x + groupsize, y);
        }
    }

    private int[] calculateAverage(int startX, int startY, int endX, int endY, int currentX, int currentY, int totalRed, int totalGreen, int totalBlue, int count) {
        if (currentY >= endY) {
            return new int[]{totalRed, totalGreen, totalBlue, count};
        }
        if (currentX >= endX) {
            return calculateAverage(startX, startY, endX, endY, startX, currentY + 1, totalRed, totalGreen, totalBlue, count);
        }
        ARGB pixel = new ARGB(inputImage.getRGB(currentX, currentY));
        int newRed = totalRed + pixel.red;
        int newGreen = totalGreen + pixel.green;
        int newBlue = totalBlue + pixel.blue;
        int newCount = count + 1;
        return calculateAverage(startX, startY, endX, endY, currentX + 1, currentY, newRed, newGreen, newBlue, newCount);
    }

    private void setPixels(int startX, int startY, int endX, int endY, int currentX, int currentY, int avgColor) {
        if (currentY >= endY) {
            return;
        }
        if (currentX >= endX) {
            setPixels(startX, startY, endX, endY, startX, currentY + 1, avgColor);
            return;
        }
        outputImage.setRGB(currentX, currentY, avgColor);
        setPixels(startX, startY, endX, endY, currentX + 1, currentY, avgColor);
    }
}
