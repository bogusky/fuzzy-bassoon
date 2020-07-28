import org.apache.commons.cli.*;
import streamer.InputTextStream;
import streamer.OutputTextStream;
import streamer.impl.*;

import java.io.*;

/**
 * The main Application implementation which triggers streaming process.
 * This class also handle application input arguments.
 */
public class Main {

    /**
     * Main class of Application.
     *
     * @param args input arguments
     */
    public static void main(String[] args) throws IOException {

        // configure commandline CLI
        Options options = new Options();
        options.addOption("c", "console", false, "stream text from console");
        options.addOption("f", "file", true, "stream text from file");
        options.addOption("b", "bytes", false, "stream text by bytes");
        options.addOption("l", "lines", false, "stream text by lines");

        // parse commandline arguments
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {

            // validate arguments
            cmd = parser.parse(options, args);
            if (cmd.hasOption("c") && cmd.hasOption("f")) {

                throw new ParseException("Choose only one input option -c or -f <file_name>");
            }
            if (cmd.hasOption("b") && cmd.hasOption("l")) {

                throw new ParseException("Choose only one output option -l or -b");
            }

            InputTextStream input;
            OutputTextStream output;

            // initialize input streams
            if (cmd.hasOption("c")) {
                input = new ConsoleInputTextStream();
            } else if (cmd.hasOption("f")) {
                input = new FileInputTextStream(cmd.getOptionValue("f"));
            } else {
                throw new ParseException("Choose at least one option -c or -f <file_name>");
            }

            // initialize output streams
            if (cmd.hasOption("b")) {
                output = new ByteOutputTextStream();
            } else if (cmd.hasOption("l")) {
                output = new LineOutputTextStream();
            } else {
                throw new ParseException("Choose at least one option -b or -c");
            }

            // start streaming
            try (TextStreamer streamer = new TextStreamer(input, output)) {
                streamer.stream();
            }
        } catch (ParseException e) {

            // print help
            System.out.println("Error parsing command-line arguments!");
            System.out.println(e.getMessage());
            System.out.println("Please, follow the instructions below:\n");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Arguments:\n", options);
            System.exit(1);
        }
    }
}
