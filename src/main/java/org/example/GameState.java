package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameState
{
    public static final int size = 3;

    private final char[][] board = new char[size][size];

    public GameState(char[][] board)
    {
        for (int row = 0; row < size; row++)
            this.board[row] = board[row].clone();
    }

    @Override
    public String toString()
    {
        return Arrays.stream(board).map(Arrays::toString).collect(Collectors.joining("\n"));
    }
}