import java.io.IOException;
//Retrieve the code used to change PixelCraft and the individual converter for grayscale initially used without this class???


public interface Converter {
/*This file is implemented in all converters and allows PixelCraft to run without 
 * adding if statements for each converter, something which would break the requirement 
 * that the PixelCraft main file not be changed. */
    void convert(String inputFileName, String outputFileName) throws IOException;
}