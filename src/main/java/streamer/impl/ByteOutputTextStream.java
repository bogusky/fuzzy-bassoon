package streamer.impl;

import streamer.OutputTextStream;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of byte based output stream.
 */
public class ByteOutputTextStream implements OutputTextStream {

    /**
     * Default value of maximum lines count per one output file
     */
    public final int DEFAULT_BYTES_COUNT = 50;

    /**
     * File name mask
     */
    public final String DEFAULT_FILE_MASK = "out-%03d.txt";

    /**
     * Output stream for writing into the file
     */
    private PrintStream out = null;

    /**
     * File counter
     */
    private int counter = 0;

    /**
     * Current written bytes
     */
    private int bytes = 0;

    /**
     * maximum bytes per one output file
     */
    private final int outputByteCount;

    /**
     * List of generated files
     */
    private List<String> files = new ArrayList<>();

    /**
     * Default constructor.
     */
    public ByteOutputTextStream() {

        outputByteCount = DEFAULT_BYTES_COUNT;
    }

    /**
     * Custom constructor.
     * @param outputByteCount maximum bytes per one output file
     */
    public ByteOutputTextStream(final int outputByteCount) {

        this.outputByteCount = outputByteCount;
    }

    /**
     * Write line into the file.
     * @param line line
     * @throws FileNotFoundException in case of problems
     */
    @Override
    public void write(String line) throws FileNotFoundException {

        if (bytes == 0) {
            initializeOutputStream();
        }

        byte[] byteSequence = line.getBytes();
        for (byte item : byteSequence) {
            if (++bytes > outputByteCount) {
                initializeOutputStream();
                bytes = 0;
            }
            out.write(item);
        }
    }

    /**
     * Initialize new resource for output stream linked with file.
     * @throws FileNotFoundException in case of problems
     */
    private void initializeOutputStream() throws FileNotFoundException {

        close();
        String fileName = String.format(DEFAULT_FILE_MASK, counter++);
        out = new PrintStream(fileName);
        files.add(fileName);
    }

    /**
     * Print all generated files during streaming process.
     */
    @Override
    public void printFiles() {

        System.out.println(String.format("Process completed. [%d] files were created.", files.size()));
        files.forEach(System.out::println);
    }

    /**
     * Close all resources.
     */
    @Override
    public void close() {

        if (out != null) {
            out.close();
        }
    }
}
