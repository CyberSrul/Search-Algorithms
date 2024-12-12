package org.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class GameState implements Node
{
    private static int builds = 0;
    public static int getBuilds() { return builds; }
    public static void clearBuilds() { builds = 0; }

    public static final int size = 3;
    public static final Map<Character, Integer> costs = Map.of('B', 1, 'G', 3, 'R', 10);

    private final char[][] board;
    private int cost;
    private GameState previous;

    public GameState(char[][] board)
    {
        builds++;
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

    private String difference(GameState other)
    {
        int row1 = -1, col1 = -1, row2 = -1, col2 = -1;
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (this.board[row][col] != other.board[row][col])
                {
                    if (this.board[row][col] == '_') { row2 = row; col2 = col; }
                    else { row1 = row; col1 = col; }
                }

        this.setCost(costs.get(this.board[row1][col1]) + other.getCost());

        return "(" + (row2+1) + "," + (col2+1) + "):" + this.board[row1][col1] +  ":(" + (row1+1) + "," + (col1+1) + ")";
    }

    public void print_path()
    {
        if (this.getPrevious() == null) return ;
        this.getPrevious().print_path();
        System.out.print(this.difference(this.getPrevious()));
        System.out.print("--");
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

                neighbor.setCost(costs.get(state.board[new_row][new_col]));
                neighbor.setPrevious(state);
                return neighbor;
            }

            return null;
        }
    }
}