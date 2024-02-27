import foobar.FileUtil;

public class Main {
    public static void main(String... args) {
        // toplevel folder
        try {
            System.out.println("\n'<wuppie>'");
            FileUtil.openResources("wuppie");
            System.out.println("'</wuppie>'\n\n");
        } catch (Exception e) {
            System.err.println("could not read 'wuppie'");
        }

        // file (complete relative path)
        try {
            System.out.println("\n'<wuppie/fluppie.txt>'");
            FileUtil.openResources("wuppie/fluppie.txt");
            System.out.println("'</wuppie/fluppie.txt>'\n\n");
        } catch (Exception e) {
            System.err.println("could not read 'wuppie/fluppie.txt'");
        }

        // folder structure (directly reachable from resources root)
        try {
            System.out.println("\n'<wuppie/fluppie>'");
            FileUtil.openResources("wuppie/fluppie");
            System.out.println("'</wuppie/fluppie>'\n\n");
        } catch (Exception e) {
            System.err.println("could not read 'wuppie/fluppie'");
        }

        // folder inside resources (path not valid)
        try {
            System.out.println("\n'<fluppie>'");
            FileUtil.openResources("fluppie");
            System.out.println("'</fluppie>'\n\n");
        } catch (Exception e) {
            System.err.println("could not read 'fluppie'");
        }

    }
}
