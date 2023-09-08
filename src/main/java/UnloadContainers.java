import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UnloadContainers {

    /*
     Starting container arrangement

                    [A] [L]     [J]
                [B] [Q] [R]     [D] [T]
                [G] [H] [H] [M] [N] [E]
            [J] [L] [D] [L] [J] [H] [B]
        [Q] [L] [W] [S] [V] [N] [F] [N]
    [W] [N] [H] [M] [L] [B] [R] [T] [Q]
    [L] [O] [C] [W] [D] [J] [W] [Z] [E]
    [S] [J] [S] [T] [O] [M] [D] [!] [H]
     1   2   3   4   5   6   7   8   9

     */

    public static void main(String[] args) throws IOException {
        mainTask();
        extOne();
    }

    public static void mainTask() throws IOException {
        String instructionsPath = System.getProperty("user.dir")+"/src/main/resources/data.txt";
        ArrayList<String> instructions = readInstructionsFromFile(instructionsPath);

        String[][] dataArray = {
                {"S","L","W"},
                {"J","O","N","Q"},
                {"S","C","H","L","J"},
                {"T","W","M","W","L","G","B"},
                {"O","D","L","S","D","H","Q","A"},
                {"M","J","B","V","L","H","R","L"},
                {"D","W","R","N","J","M"},
                {"!","Z","T","F","H","N","D","J"},
                {"H","E","Q","N","B","E","T"}
        };
        ArrayList<ArrayList<String>> data = convertToArrayList(dataArray);

        var message = processContainers(data, instructions);
        System.out.println(message);
    }

    public static void extOne() throws IOException {
        String instructionsPath = System.getProperty("user.dir")+"/src/main/resources/data1.txt";
        ArrayList<String> instructions = readInstructionsFromFile(instructionsPath);

        String[][] dataArray = {
                {"S","L","W"},
                {"J","T","T","Q"},
                {"S","C","H","F","J"},
                {"T","F","M","W","T","G","B"},
                {"S","A","L","S","D","H","Q","B"},
                {"M","J","B","V","N","H","R","L"},
                {"D","W","R","N","J","M"},
                {"C","Z","T","F","H","N","D","J"},
                {"H","A","Q","N","B","I","T"}
        };
        ArrayList<ArrayList<String>> data = convertToArrayList(dataArray);

        var message = processContainers(data, instructions);
        System.out.println(message);
    }

    /**
     * Read the instructions from a file
     * @param filePath The path to the file
     * @return The instructions
     * @throws IOException If the file cannot be read or found
     */
    public static ArrayList<String> readInstructionsFromFile(String filePath) throws IOException {
        ArrayList<String> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                instructions.add(line);
            }
        }
        return instructions;
    }

    public static ArrayList<ArrayList<String>> convertToArrayList(String[][] dataArray) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (String[] row : dataArray) {
            ArrayList<String> rowList = new ArrayList<>(Arrays.asList(row));
            data.add(rowList);
        }
        return data;
    }

    /**
     * Process the containers according to the instructions
     * @param data The current grid of containers
     * @param instructions The instructions to execute
     * @return The message encoded in the containers
     */
    public static String processContainers(ArrayList<ArrayList<String>> data, ArrayList<String> instructions) {
        for (String instruction : instructions) {
            doIteration(data, instruction);
            // printContainers(data);
        }

        // Get the top item in each column
        StringBuilder message = new StringBuilder();
        for (ArrayList<String> column : data) {
            if (column.size() > 0) {
                message.append(column.get(column.size() - 1));
            }
        }

        return message.toString();
    }

    /**
     * Execute a single instruction, moves containers in memory, mutates the underlying arraylists.
     * @param data The current grid of containers
     * @param instruction The instruction to execute
     */
    public static void doIteration(ArrayList<ArrayList<String>> data, String instruction) {
        // I prompted copilot to use a regex here but this is what it spat out...
        String[] splitInstruction = instruction.split(" ");
        int containersToMove = Integer.parseInt(splitInstruction[1]);
        int fromColumn = Integer.parseInt(splitInstruction[3]);
        int toColumn = Integer.parseInt(splitInstruction[5]);

        ArrayList<String> columnFrom = data.get(fromColumn - 1);
        ArrayList<String> columnTo = data.get(toColumn - 1);

        // Move containers one by one taking from the top first (like a crane irl) and plonking on the target column
        for (int i = 0; i < containersToMove; i++) {
            String container = columnFrom.remove(columnFrom.size() - 1);
            columnTo.add(container);
        }
    }

    /**
     * Print the containers in a grid format like in the example.
     * Thanks copilot - It works but my mind glazes over trying to read this.
     * @param data The current grid of containers
     */
    public static void printContainers(ArrayList<ArrayList<String>> data) {
        int numRows = data.size();
        int numCols = 0;
        for (ArrayList<String> row : data) {
            if (row.size() > numCols) {
                numCols = row.size();
            }
        }
        String[][] grid = new String[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (j < data.get(i).size()) {
                    grid[numCols - j - 1][i] = data.get(i).get(j);
                } else {
                    grid[numCols - j - 1][i] = "";
                }
            }
        }
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                String container = grid[i][j];
                if (container.isEmpty()) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[" + container + "]");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
