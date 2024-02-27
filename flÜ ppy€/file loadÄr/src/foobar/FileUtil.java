package foobar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;


public final class FileUtil {

    /**
     * Open the resource given as parameter as Path.
     * <p>
     * Use a new FileSystem if inside JAR, or just a Path otherwise.
     *
     * @param resource in assets folder, can be a specific file or a folder
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void openResources(String resource) throws URISyntaxException, IOException {

        // get URI of resource relative to current process (thread)
        // we can't use a given class here - the resource would be resolved relative to this class, i.e. relative to a given (sub-) project
        // * e.g. using Crafting.class.getResource(resource).toURI() would resolve relative to subproject "dungeon"
        // * e.g. using DrawComponent.class.getResource(resource).toURI() would resolve relative to subproject "game"
        URI uri = Thread.currentThread().getContextClassLoader().getResource(resource).toURI();


        // get Path to resource
        // * in local file system just use the URI
        // * in JAR we need to create a new FileSystem on top of the URI
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fs;
            try {
                fs = FileSystems.getFileSystem(uri);
            } catch (FileSystemNotFoundException fsnfe) {
                fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
            }
            myPath = fs.getPath(resource); // absolute to/in JAR, relative to resources root
        } else {
            myPath = Paths.get(uri); // absolute to local file system
        }


        // do something: traverse
        if (Files.isDirectory(myPath)) {
            System.out.println("content (Folder):");
            Files.walk(myPath).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".txt")).forEach(System.out::println);
            Files.walk(myPath).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".txt")).forEach(FileUtil::readResource);
        } else if (Files.isRegularFile(myPath)) {
            System.out.println("content (File): " + myPath);
            readResource(myPath);
        }

/*
JAR: Pfad via FileSystem holen => absolut in JAR, relativ zu Resources Root (Local FS)
Local FS: Pfad via Default-FS holen => absolut (Local FS)

Iteration: Files.walk -> foreach path

Einlesen:
  - JAR: new BufferedReader(new InputStreamReader(clazz.class.getResourceAsStream(path.toString())));
  - Local FS: BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

//        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

Crafting: Alles in einem Ordner, je: private static Recipe parseRecipe(final InputStream stream, final String name) => liest aus InputStream
DrawComponents: Uff.
 */
    }

    /**
     * Read the resource specified.
     * <p>
     * If the parameter is an absolute path, we open a new InputStream on it. (This would be usually the case for
     * resources when running from a local filesystem.)
     * <p>
     * Otherwise we try to resolve it via the resources of the current process (thread). (This would be usually the
     * case when running from a JAR file.)
     *
     * @param p Path to process.
     */
    public static void readResource(Path p) {

        // open an InputStream
        // * path is absolute: use the local file system
        // * path is relative: resolve in resources of current process
        InputStream wuppie;
        if (p.isAbsolute()) {
            try {
                wuppie = Files.newInputStream(p);
                System.out.println("\nlocal filesystem: " + p + ":");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            wuppie = Thread.currentThread().getContextClassLoader().getResourceAsStream(p.toString());
            System.out.println("\nJAR: " + p + ":");
        }


        // now let's do something with our file ...
        // TODO: to be replaced by working a lambda callback
        BufferedReader r = new BufferedReader(new InputStreamReader(wuppie));
        String s;
        while (true) {
            try {
                if ((s = r.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\t" + s);
        }
    }
}
