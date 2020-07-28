package streamer.impl;

import streamer.InputTextStream;
import streamer.OutputTextStream;

import java.io.Closeable;
import java.io.IOException;

/**
 * Implementation of streamer which handle streaming process.
 */
public class TextStreamer implements Closeable {

    /**
     * Input stream fom reading the data from particular input type
     */
    private final InputTextStream input;

    /**
     * Output stream for writing outputs into particular output type
     */
    private final OutputTextStream output;

    /**
     * Constructor.
     *
     * @param input implementation of input stream
     * @param output implementation of output stream
     */
    public TextStreamer(final InputTextStream input, final OutputTextStream output) {

        if (input == null || output == null) {
            throw new IllegalArgumentException("Input and output stream cannot be null!");
        }

        this.input = input;
        this.output = output;
    }

    /**
     * Entry point to streaming process.
     *
     * @throws IOException in case of problems with reading or writing into the file.
     */
    public void stream() throws IOException {

        input.stream(output);
        output.printFiles();
    }

    /**
     * Close resources.
     *
     * @throws IOException in case of problems
     */
    @Override
    public void close() throws IOException {

        input.close();
        output.close();
    }
}
