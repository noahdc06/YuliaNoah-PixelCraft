import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class rotate {
    public static void main(String[] args) {
        try {
            BufferedImage inputImage = ImageIO.read(new File("projexample.png"));
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int n = inputImage.getRGB(x, y);
                    rotatedImage.setRGB(height - 1 - y, x, n);
                }
            }

            SwingUtilities.invokeLater(() -> displayImage(rotatedImage));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame("Rotated Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
