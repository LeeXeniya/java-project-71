package hexlet.code;

//public class App {
//   public static void main(String[] args) {
//       System.out.println("Hello World!");
//    }
//}
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, //version = "checksum 4.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file", defaultValue = "/etc/hosts")
    private File filepath1 = new File("/etc/hosts");

    @Parameters(index = "0", description = "path to second file", defaultValue = "/etc/hosts")
    private File filepath2 = new File("/etc/hosts");

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "MD5";

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        byte[] fileContents = Files.readAllBytes(filepath1.toPath());
        byte[] digest = MessageDigest.getInstance(format).digest(fileContents);
        System.out.printf(format + " hash of " + filepath2.getPath() + ": %0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
        return 0;
    }
}