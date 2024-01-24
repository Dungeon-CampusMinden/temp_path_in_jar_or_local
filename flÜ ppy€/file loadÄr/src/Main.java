import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;

public class Main {
    public static void main(String... args) throws URISyntaxException, IOException {
        String folder = "wuppie";
        URI uri = Main.class.getResource(folder).toURI();

        Path myPath;
        if (uri.getScheme().equals("jar")) {
            // inside JAR
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath(folder);
        } else {
            // normal filesystem, e.g. in IDE
            myPath = Paths.get(uri);
        }

        Files.walk(myPath, 2).forEach(System.out::println);
    }
}
