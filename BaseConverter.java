import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/* We used this abstract class to replace the ImageIO read and write 
 * functionalities which were the same in every converter, to more easily
 * manage image files, implementing Converter */
public abstract class BaseConverter implements Converter {
	// Simple method to read the input image, used in every class
    public BufferedImage readImage(String filename) throws IOException {
        return ImageIO.read(new File(filename));
    }
    // Simple method to write the converted image, used in every class
    public void writeImage(BufferedImage image, String filename) throws IOException {
        ImageIO.write(image, "png", new File(filename));
    }
}
