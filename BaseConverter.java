import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class BaseConverter implements Converter {

    protected BufferedImage readImage(String filename) throws IOException {
        return ImageIO.read(new File(filename));
    }

    protected void writeImage(BufferedImage image, String filename) throws IOException {
        ImageIO.write(image, "png", new File(filename));
    }
}
