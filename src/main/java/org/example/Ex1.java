package org.example;

import java.io.*;
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

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt"))))
        {
            method = reader.readLine();
            time = reader.readLine();
            open = reader.readLine();
            initialState = loadState(reader);
            reader.readLine();
            goalState = loadState(reader);


            // choosing algorithm
            Algorithm algo = switch (method)
            {
                case "A*", "BFS" -> new Astar();
                case "IDA*", "DFID" -> new IDAstar();
                case "DFBnB" -> new BnB();
                default -> throw new IllegalArgumentException("Unknown method: " + method);
            };

            // choosing cost function
            BiFunction<Node, Node, Integer> newCost = switch (method)
            {
                case "A*", "IDA*", "DFBnB" -> (src, dst) -> src.getCost() + dst.getCost() - heuristic((GameState) src, goalState) + heuristic((GameState) dst, goalState);
                case "BFS", "DFID" -> (src, _) -> src.getCost() + 1;
                default -> throw new IllegalArgumentException("Unknown method: " + method);
            };


            // executing search
            GameState.clearBuilds();

            long startTime = System.nanoTime();
            GameState solution = (GameState) algo.run(initialState, goalState, open.equals("with open"), newCost);
            long totalTime = System.nanoTime() - startTime;

            writer.println(solution != null ? solution.getPath() : "no path");
            writer.println("Num: " + GameState.getBuilds());
            writer.println("Cost: " + (solution != null ? solution.getCost() : "inf"));
            if (time.equals("with time"))writer.println(String.format("%.3f seconds", totalTime / 1e+9));
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


    public static int heuristic(GameState node, GameState goal)
    {
        int estimation = 0;

        for (int row = 0; row < GameState.size; row++)
            for (int col = 0; col < GameState.size; col++)
                if (GameState.costs.containsKey(goal.getCell(row, col)))
                    if (node.getCell(row, col) != goal.getCell(row, col))
                        estimation += GameState.costs.get(goal.getCell(row, col));

        return estimation;
    }
}