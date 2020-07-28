package streamer.impl;

import streamer.OutputTextStream;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of line based output stream.
 */
public class LineOutputTextStream implements OutputTextStream {

    /**
     * Default value of maximum lines count per one output file
     */
    public final int DEFAULT_LINES_COUNT = 5;

    /**
     * Output file name mask
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
     * Current line position
     */
    private int lines = 0;

    /**
     * maximum lines count per one output file
     */
    private final int outputLinesCount;

    /**
     * List of generated files
     */
    private List<String> files = new ArrayList<>();

    /**
     * Default constructor.
     */
    public LineOutputTextStream() {

        outputLinesCount = DEFAULT_LINES_COUNT;
    }

    /**
     * Custom constructor.
     * @param outputLinesCount maximum lines per one output file
     */
    public LineOutputTextStream(final int outputLinesCount) {

        this.outputLinesCount = outputLinesCount;
    }

    /**
     * Write line into the file.
     * @param line line
     * @throws FileNotFoundException in case of problems
     */
    @Override
    public void write(final String line) throws FileNotFoundException {

        if (++lines > outputLinesCount) {
            lines = 1;
        }

        if (lines == 1) {
            initializeOutputStream();
        }

        out.println(line);
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
        files.stream().forEach(System.out::println);
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
