import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
//8x12 or 96 blocks for 400x600 pixels
//29x23 or 667 blocks for 1440x1148
public class Jigsaw extends BaseConverter {
    private static final int TILE_SIZE = 50;
    private BufferedImage inputImage, outputImage;
    private int width, height;

    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        inputImage = readImage(inputFileName);
        width = inputImage.getWidth();
        height = inputImage.getHeight();
        outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        shuffleTiles(0, 0); //Recursive call here
        writeImage(outputImage, outputFileName);
    }

    private void shuffleTiles(int tileX, int tileY) {
        if (tileY >= height) return;
        
        if (tileX >= width) {
            shuffleTiles(0, tileY + TILE_SIZE);
        } else {
            // Recursive tile processing (swap with random tile)
            int swapX = (int)(Math.random() * (width/TILE_SIZE)) * TILE_SIZE;
            int swapY = (int)(Math.random() * (height/TILE_SIZE)) * TILE_SIZE;
            
            swapTiles(tileX, tileY, swapX, swapY, 0, 0);
            shuffleTiles(tileX + TILE_SIZE, tileY);
        }
    }

    private void swapTiles(int x1, int y1, int x2, int y2, int dx, int dy) {
        if (dy >= TILE_SIZE) return;
        
        if (dx >= TILE_SIZE) {
            swapTiles(x1, y1, x2, y2, 0, dy + 1);
        } else {
            int px1 = x1 + dx, py1 = y1 + dy;
            int px2 = x2 + dx, py2 = y2 + dy;
            
            if (px1 < width && py1 < height && px2 < width && py2 < height) {
                int temp = inputImage.getRGB(px1, py1);
                outputImage.setRGB(px1, py1, inputImage.getRGB(px2, py2));
                outputImage.setRGB(px2, py2, temp);
            }
            
            swapTiles(x1, y1, x2, y2, dx + 1, dy);
        }
    }
}

