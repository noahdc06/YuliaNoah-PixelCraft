import java.io.IOException;
/* File's initial purpose was to be implemented in every converter to allow PixelCraft
 * to run without adding if statements for each converter, which would break the
 * requirement that the PixelCraft main file not be changed.*/

public interface Converter {
/* Once we tried using inheritance, we only needed to implement this into the
 * BaseConverter file, as that abstract class does all the file management, and
 * we can connect to each converter through extending BaseConverter to them
 * individually.*/
    void convert(String inputFileName, String outputFileName) throws IOException;
}
