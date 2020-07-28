package streamer.impl;

import streamer.InputTextStream;
import streamer.OutputTextStream;

import java.io.*;

/**
 * Implementation of file input stream.
 */
public class FileInputTextStream implements InputTextStream {

    /**
     * Input buffered file reader.
     */
    private final BufferedReader input;

    /**
     * Consturctor.
     *
     * @param file input file path
     * @throws FileNotFoundException in case of input file doesn't exist
     */
    public FileInputTextStream(String file) throws FileNotFoundException {

        input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    /**
     * Start streaming from file input into particular output stream.
     *
     * @param output output stream
     * @throws IOException in case of problems
     */
    @Override
    public void stream(final OutputTextStream output) throws IOException {

        if (output == null) {
            throw new IllegalArgumentException("Output stream cannot be null!");
        }

        while(input.ready()) {
            output.write(input.readLine());
        }
    }

    /**
     * Close all resources.
     * @throws IOException in case of problems
     */
    @Override
    public void close() throws IOException {

        input.close();
    }
}
