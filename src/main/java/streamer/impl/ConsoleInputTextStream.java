package streamer.impl;

import streamer.InputTextStream;
import streamer.OutputTextStream;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implementation of console input stream.
 */
public class ConsoleInputTextStream implements InputTextStream {

    /**
     * Console stream.
     */
    private final Scanner in = new Scanner(System.in);

    /**
     * Start streaming process from console input.
     * @param output output stream
     * @throws FileNotFoundException in case of problems
     */
    @Override
    public void stream(final OutputTextStream output) throws FileNotFoundException {

        if (output == null) {
            throw new IllegalArgumentException("Output stream cannot be null!");
        }

        String line;
        while (in.hasNextLine() && ! (line = in.nextLine()).equalsIgnoreCase("")) {

            output.write(line);
        }
    }

    /**
     * Close all resources
     */
    @Override
    public void close() {

        in.close();
    }
}
