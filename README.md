## Welcome brave developer!

This is a program that turns a 'streamed' input into an output, slicing it with a given criteria. So far, it takes input from stdin, and puts the sliced/chunked files to the local dir with some format.

### Assignment
1. Read and understand the code.

1. Critique (a simple enumeration of the main issues found written into an .md file is more than enough) and refactor this code according to the problems you see here. Do not limit yourself to the Main.java file. Create as many files and classes as you need to get a solid implementation.

1. Add one of this custom changes
    1. Change the input mechanism so that the program takes data from a text file instead of the stdin. This input file path should be provided as a program argument.
    1. Change the output mechanism so that the slicing happens every 50 bytes, instead of every 5 lines, but avoid splitting the lines. This means that if the 50th byte is in the middle of a line, that line must be completely written into the file before splitting the output file. This is so, in case any line is longer than 50 bytes, so that no file ends up empty.

## Solution (Michal Bogusky)
1. Original solution reads the lines from stdin and stream it into the output files sliced into 5 lines

1. Issues
    1. the input stream is never closed
    1. implementation of reading from stdio is not very readable. Instead of reading initial line from console and creating / closing the output files on two places there is possible to implement it using single "while" without code duplicities
    1. implementation of printing list of generated files is based on output directory listing, this caused all files in the directory being listed, not only the newly created files
    1. some input variables like file name mask should be defined as constants
    1. whole solution is very hardcoded for current requirements and each new extension with another requirements would need complete refactoring
    1. there is one output file created even for an empty input
    1. no javadoc
    1. no unit tests
    1. pom.xml should define java version for java compiler  
   
1. Both custom changes has been implemented
    1. generic interfaces for input and output streams have been added into the project "InputTextStream, OutputTextStream"
    1. implementation of input stream reading data from the stdio has been moved into "ConsoleInputTextStream"
    1. implementation of input stream reading data from file has been added into "FileInputTextStream"
    1. implementation of line based output stream has been moved into "LineOutputTextStream"
    1. implementation of byte based output stream has been added into "ByteOutputTextStream" 
    1. handling of streaming process has been moved into a separate class "TextStreamer"
    1. input parameters of "TextStreamer" constructor are particular implementations of InputTextStream and OutputTextStream interfaces
    1. "Main" class now implements parsing and handling input arguments of application. It instantiates particular input/output streams implementation based on input parameters and calls the streaming entry point from "TextStreamer" 
    
 ### How to build application
    
    mvn clean package
 
 ### How to run application
 Run application with following arguments:
 
    java -jar target/FileSlicer-1.0-SNAPSHOT-jar-with-dependencies.jar <input_params> <output_params>
 
 input_params:
 
    -c or --console             reads data from stdio
    -f or --file <file_name>    reads data from input file
    
 output_params:
    
    -l or --lines               for generating lines based output
    -b or --bytes               for generating bytes based output
    
 #### Examples:
 
 Run console input and line based output streaming
 
     java -jar target/FileSlicer-1.0-SNAPSHOT-jar-with-dependencies.jar -c -l
    
 Run file input and bytes based output streaming
 
    java -jar target/FileSlicer-1.0-SNAPSHOT-jar-with-dependencies.jar -f input.txt -b