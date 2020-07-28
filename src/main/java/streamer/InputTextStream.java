package streamer;

import java.io.Closeable;
import java.io.IOException;

/**
 * Generic interface for input streams
 */
public interface InputTextStream extends Closeable {

    /**
     * Start streaming from input to particular output stream.
     *
     * @param output output stream
     * @throws IOException in case of problems
     */
    void stream(OutputTextStream output) throws IOException;
}
