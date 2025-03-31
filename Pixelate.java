import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This program aims to create a 'pixelate' effect by taking a 15x15 pixel block, 
 * calculating the average of each individual ARGB value for all pixels across
 * that block, and setting every pixel within that block to that color.*/
public class Pixelate extends BaseConverter {
	// Variable declaration for variables which do not change throughout recursion
    private BufferedImage inputImage, outputImage;
    private int width, height;

    // Base method to hold and control all unchanging variables
    public void convert(String inputFileName, String outputFileName) throws IOException {
        inputImage = readImage(inputFileName);
        width = inputImage.getWidth();
        height = inputImage.getHeight();
        outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Begins recursive processing of each block
        processBlock(0, 0);
        writeImage(outputImage, outputFileName);
    }

    /* Recursive method to create blocks of pixels and then
     * replace every pixel with a new one using the average
     * ARGB values. */
    private void processBlock(int x, int y) {
        // Base case: last block reached, do nothing
        if (y >= height) return;

        // Ensures the block does not go out of bounds
        int endX = Math.min(x + 15, width);
        int endY = Math.min(y + 15, height);

        /* Begins recursive gathering of ARGB values, putting each value
         * into an array, and also saving the total number of pixels.*/
        int[] totals = calculateAverage(x, y, endX, endY, x, y, 0, 0, 0, 0);
        
        // Creates a new pixel using the average of each ARGB value
        int avgRed = totals[0] / totals[3];
        int avgGreen = totals[1] / totals[3];
        int avgBlue = totals[2] / totals[3];
        ARGB avgColor = new ARGB(255, avgRed, avgGreen, avgBlue);

        // Begins recursive replacing of each pixel within the block
        setPixels(x, y, endX, endY, x, y, avgColor.toInt());

        
        if (x + 15 >= width) {
        	// Calls itself, moving to the next row
            processBlock(0, y + 15);
        } else {
        	//Calls itself, iterating through each block column
            processBlock(x + 15, y);
        }
    }
    
    // Recursive method to gather ARGB values of each pixel in a block
    private int[] calculateAverage(int startX, int startY, int endX, int endY, int currentX, int currentY, int totalRed, int totalGreen, int totalBlue, int count) {
        // Base case: last pixel reached
    	if (currentY >= endY) {
            return new int[]{totalRed, totalGreen, totalBlue, count};
        }
        if (currentX >= endX) {
        	// End of column reached/exceeded: Calls itself, moving to the next row of pixels
            return calculateAverage(startX, startY, endX, endY, startX, currentY + 1, totalRed, totalGreen, totalBlue, count);
        }
        // Adds the individual ARGB values to the array
        ARGB pixel = new ARGB(inputImage.getRGB(currentX, currentY));
        int newRed = totalRed + pixel.red;
        int newGreen = totalGreen + pixel.green;
        int newBlue = totalBlue + pixel.blue;
        // Increases the count for how many pixels there are
        int newCount = count + 1;
        // Calls itself, iterating through each pixel column
        return calculateAverage(startX, startY, endX, endY, currentX + 1, currentY, newRed, newGreen, newBlue, newCount);
    }
    
    // Recursive method to replace every pixel with the same pixel in that block
    private void setPixels(int startX, int startY, int endX, int endY, int currentX, int currentY, int avgColor) {
        // Base case: last pixel reached, do nothing
    	if (currentY >= endY) {
            return;
        }
        if (currentX >= endX) {
        	//  End of column reached/exceeded: calls itself, moving to the next row of pixels
            setPixels(startX, startY, endX, endY, startX, currentY + 1, avgColor);
            return;
        }
        // Replaces the pixel at the coordinate
        outputImage.setRGB(currentX, currentY, avgColor);
        // Calls itself, iterating through each pixel column
        setPixels(startX, startY, endX, endY, currentX + 1, currentY, avgColor);
    }
}
