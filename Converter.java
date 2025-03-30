import java.io.IOException;


public interface Converter {
/*We initially had this interface implemented in EVERY converter, then later realized
 **/
    void convert(String inputFileName, String outputFileName) throws IOException;
}
