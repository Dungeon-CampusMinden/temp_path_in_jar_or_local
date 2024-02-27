package foobar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileUtilTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void openResourcesFolderSimple() {
        String expected = """
            content (Folder):
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_right/knight_idle_right_1.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_right/knight_idle_right_2.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_2.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_3.txt

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_right/knight_idle_right_1.txt:
            	knight_idle_right_1

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_right/knight_idle_right_2.txt:
            	knight_idle_right_2

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt:
            	knight_idle_left_1

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_2.txt:
            	knight_idle_left_2

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_3.txt:
            	knight_idle_left_3
            """;
        try {
            FileUtil.openResources("knight");
        } catch (Exception e) {
            fail();
        }
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void openResourcesFolderDouble() {
        String expected = """
            content (Folder):
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_2.txt
            /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_3.txt

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt:
            	knight_idle_left_1

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_2.txt:
            	knight_idle_left_2

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_3.txt:
            	knight_idle_left_3
            """;
        try {
            FileUtil.openResources("knight/idle_left");
        } catch (Exception e) {
            fail();
        }
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void openResourcesFile() {
        String expected = """
            content (File): /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt

            local filesystem: /PATH/temp_path_in_jar_or_local/flÜ ppy€/file loadÄr/build/resources/test/knight/idle_left/knight_idle_left_1.txt:
            	knight_idle_left_1
            """;
        try {
            FileUtil.openResources("knight/idle_left/knight_idle_left_1.txt");
        } catch (Exception e) {
            fail();
        }
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void readResource() {
    }
}
