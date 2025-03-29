import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class rotate implements Converter {
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputFileName));
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = inputImage.getRGB(x, y);
                outputImage.setRGB(height - 1 - y, x, n);
            }
        }

        // Save the output image instead of displaying it
        ImageIO.write(outputImage, "png", new File(outputFileName));
    }
}


