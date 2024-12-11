package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiFunction;

public class Ex1
{
    public static void main(String[] args) throws IOException
    {
        String method;
        String time;
        String open;
        GameState initialState;
        GameState goalState;

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt")))
        {
            // Read method and predicates
            method = reader.readLine();
            time = reader.readLine();
            open = reader.readLine();
            initialState = loadState(reader);
            reader.readLine();
            goalState = loadState(reader);
        }

        // Printing loaded information (for demonstration purposes)
        System.out.println("Method: " + method);
        System.out.println("Time: " + time);
        System.out.println("Open: " + open);
        System.out.println("Initial State:");
        System.out.println(initialState);
        System.out.println("Goal State:");
        System.out.println(goalState);

        System.out.println("Running algorithm...");
        Node solution = new A_star().run(initialState, goalState, false, (a, b) -> 1);

        if (solution == null) System.out.println("No solution found.");
        print_path(solution);
    }

    private static void print_path(Node solution)
    {
        if (solution == null) return;
        print_path(solution.getPrevious());
        System.out.println(solution);
        System.out.println();
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