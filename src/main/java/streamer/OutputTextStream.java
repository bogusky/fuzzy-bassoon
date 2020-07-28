package streamer;

import java.io.Closeable;
import java.io.FileNotFoundException;

/**
 * Generic interface for output streams
 */
public interface OutputTextStream extends Closeable {

    /**
     * Write line from input into output stream.
     * @param line line
     * @throws FileNotFoundException in case of problems
     */
    void write(String line) throws FileNotFoundException;

    /**
     * Print information about generated files.
     */
    void printFiles();
}
