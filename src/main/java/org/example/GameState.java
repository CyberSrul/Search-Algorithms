package org.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class GameState implements Node
{
    public static final int size = 3;

    private final char[][] board;
    private int cost;
    private GameState previous;

    public GameState(char[][] board)
    {
        this.cost = 0;
        this.previous = null;

        this.board = new char[size][size];
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
            {
                if (! "GBR_X".contains("" + board[row][col]))
                    throw new IllegalArgumentException("Invalid board");

                this.board[row][col] = board[row][col];
            }
    }

    @Override
    public int getCost(){ return this.cost; }
    @Override
    public GameState getPrevious() { return this.previous; }
    @Override
    public void setCost(int cost) { this.cost = cost; }
    @Override
    public void setPrevious(Node previous){ this.previous = (GameState) previous; }


    @Override
    public String toString()
    {
        return Arrays.stream(board).map(Arrays::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(board); }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameState that = (GameState) o;
        return Arrays.deepEquals(this.board, that.board);
    }

    @Override
    public Iterable<Node> neighbors()
    {
        return new NeighborIterator(this);
    }

    private static class NeighborIterator implements Iterable<Node>, Iterator<Node>
    {
        private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        private int row, col, dir;
        private final GameState state;

        public NeighborIterator(GameState gameState)
        {
            this.row = this.col = this.dir = 0;
            this.state = gameState;
        }

        @Override
        public Iterator<Node> iterator() { return this; }

        @Override
        public boolean hasNext() { return row < size; }

        private void advance()
        {
            if (++col < size) return;
            col = 0;
            row++;
        }

        @Override
        public Node next()
        {
            while (hasNext())
            {
                if (state.board[row][col] != '_') { advance(); continue; }
                if (dir == directions.length) { dir = 0; advance(); continue; }

                int new_row = (row + directions[dir][0]) % size; if (new_row < 0) new_row = size-1;
                int new_col = (col + directions[dir][1]) % size; if (new_col < 0) new_col = size-1;
                dir++;

                if (state.board[new_row][new_col] == 'X' || state.board[new_row][new_col] == '_') continue;

                GameState neighbor = new GameState(state.board);

                char temp = neighbor.board[row][col];
                neighbor.board[row][col] = neighbor.board[new_row][new_col];
                neighbor.board[new_row][new_col] = temp;

                if (state.board[new_row][new_col] == 'B') neighbor.cost = 1;
                if (state.board[new_row][new_col] == 'G') neighbor.cost = 3;
                if (state.board[new_row][new_col] == 'R') neighbor.cost = 9;
                neighbor.setPrevious(state);
                return neighbor;
            }

            return null;
        }
    }
}