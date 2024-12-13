package org.example;

import java.io.*;

public class Ex1
{
    public static void main(String[] args) throws IOException {
        String method;
        String time;
        String open;
        GameState initialState;
        GameState goalState;

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")))) // Added file writer
        {
            // Read method and predicates
            method = reader.readLine();
            time = reader.readLine();
            open = reader.readLine();
            initialState = loadState(reader);
            reader.readLine();
            goalState = loadState(reader);

            // Redirect output to the file
            writer.println("Method: " + method);
            writer.println("Time: " + time);
            writer.println("Open: " + open);
            writer.println("Initial State:");
            writer.println(initialState);
            writer.println("Goal State:");
            writer.println(goalState);

            writer.println("Running algorithm...");
            GameState.clearBuilds();
            GameState solution = (GameState) new A_star().run(initialState, goalState, true, (a, _) -> a.getCost() + 1);

            if (solution == null) writer.println("No solution found.");
            assert solution != null;

            writer.println("Solution: " + solution.getPath());
            writer.println();
            writer.println("Num: " + GameState.getBuilds());
            writer.println("Cost: " + solution.getCost());
        }
    }

    private static GameState loadState(BufferedReader reader) throws IOException
    {
        char[][] board = new char[GameState.size][GameState.size];

        for (int row = 0; row < GameState.size; row++)
        {
            String[] cells = reader.readLine().split(",");
            for (int col = 0; col < GameState.size; col++)
                board[row][col] = cells[col].trim().charAt(0);
        }

        return new GameState(board);
    }
}