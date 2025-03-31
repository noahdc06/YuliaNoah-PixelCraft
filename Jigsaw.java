import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* This code aims to create a 'jigsaw' effect by separating the image into 50x50
 * tiles and randomly swapping them. */
public class Jigsaw extends BaseConverter {
	// Variable declaration for variables which do not change throughout recursion
    private BufferedImage inputImage, outputImage;
    private int width, height;

    // Base method to hold and control all unchanging variables
    public void convert(String inputFileName, String outputFileName) throws IOException {
    	// Uses BaseConverter method to get the input image
        inputImage = readImage(inputFileName);
        width = inputImage.getWidth();
        height = inputImage.getHeight();
        // Creates the frame for the output image
        outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Begins recursive shuffling of tiles
        shuffleTiles(0, 0);
        // Uses BaseConverter method to output the image to PixelCraft
        writeImage(outputImage, outputFileName);
    }
    
    //Recursive method to select which tiles should be shuffled.
    private void shuffleTiles(int tileX, int tileY) {
    	// Base case: The bottom tile is reached, do nothing
        if (tileY >= height) return;
        
        // Recursively calls itself, iterating through each tile column and then through each row
        if (tileX >= width) {
            shuffleTiles(0, tileY + 50);
        } else {
            //Selects another tile randomly to 'swap positions' with
            int swapX = (int)(Math.random() * (width/50)) * 50;
            int swapY = (int)(Math.random() * (height/50)) * 50;
            // Begins recursive swapping of each pixel
            swapPixels(tileX, tileY, swapX, swapY, 0, 0);
            shuffleTiles(tileX + 50, tileY);
        }
    }
    
    //Recursive method to swap the individual pixels of the selected tiles.
    private void swapPixels(int x1, int y1, int x2, int y2, int dx, int dy) {
    	//Base case: The bottom pixel is reached, do nothing
        if (dy >= 50) return;
        // Recursively calls itself for 50 pixels, iterating through each pixel column and then through each row
        if (dx >= 50) {
            swapPixels(x1, y1, x2, y2, 0, dy + 1);
        } else {
        	// Gets the pixel coordinates of the first tile and the tile it is swapping with
            int px1 = x1 + dx;
            int py1 = y1 + dy;
            int px2 = x2 + dx;
            int py2 = y2 + dy;
            //Checks to ensure the pixel is within bounds, otherwise do nothing
            if (px1 < width && py1 < height && px2 < width && py2 < height) {
            	//Swaps the pixels between the two tiles, using temp to hold the first pixel during the swap
                int temp = inputImage.getRGB(px1, py1);
                outputImage.setRGB(px1, py1, inputImage.getRGB(px2, py2));
                outputImage.setRGB(px2, py2, temp);
            }
            swapPixels(x1, y1, x2, y2, dx + 1, dy);
        }
    }
}
