import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class grayscale extends BaseConverter {
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = inputImage.getRGB(x, y);
                ARGB argb = new ARGB(pixel);
                int average = (argb.red + argb.green + argb.blue) / 3;
                ARGB grayscaleArgb = new ARGB(argb.alpha, average, average, average);
                outputImage.setRGB(x, y, grayscaleArgb.toInt());
            }
        }

        // Save the output image instead of displaying it
        writeImage(outputImage, outputFileName);
    }
}
