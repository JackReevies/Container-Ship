import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class UnloadContainersTest {
    @Test
    public void processContainers_example() {
        String[][] dataArray = {
                {"Z", "N"},
                {"M", "C", "D"},
                {"P"}
        };
        ArrayList<ArrayList<String>> data = UnloadContainers.convertToArrayList(dataArray);

        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("move 1 from 2 to 1");
        instructions.add("move 3 from 1 to 3");
        instructions.add("move 2 from 2 to 1");
        instructions.add("move 1 from 1 to 2");

        var result = UnloadContainers.processContainers(data, instructions);

        assertEquals("C", data.get(0).get(0));
        assertEquals("M", data.get(1).get(0));
        assertEquals("P", data.get(2).get(0));
        assertEquals("D", data.get(2).get(1));
        assertEquals("N", data.get(2).get(2));
        assertEquals("Z", data.get(2).get(3));
        assertEquals("CMZ", result);
    }

    @Test
    public void readInstructionsFromFile() throws IOException {
        ArrayList<String> instructions = UnloadContainers.readInstructionsFromFile(System.getProperty("user.dir")+"/src/main/resources/data0.txt");
        assertEquals(4, instructions.size());
        assertEquals("move 1 from 2 to 1", instructions.get(0));
        assertEquals("move 3 from 1 to 3", instructions.get(1));
        assertEquals("move 2 from 2 to 1", instructions.get(2));
        assertEquals("move 1 from 1 to 2", instructions.get(3));
    }

    @Test
    public void doIteration_firstStepFromExample() {
        String[][] dataArray = {
                {"Z", "N"},
                {"M", "C", "D"},
                {"P"}
        };
        ArrayList<ArrayList<String>> data = UnloadContainers.convertToArrayList(dataArray);

        UnloadContainers.doIteration(data, "move 1 from 2 to 1");

        assertEquals("Z", data.get(0).get(0));
        assertEquals("N", data.get(0).get(1));
        assertEquals("D", data.get(0).get(2));
        assertEquals("M", data.get(1).get(0));
        assertEquals("C", data.get(1).get(1));
        assertEquals("P", data.get(2).get(0));
    }
}