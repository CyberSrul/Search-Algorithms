package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest
{
    char[][] board1, board2;
    GameState state1, state2;

    @BeforeEach
    void setUp()
    {
        assertDoesNotThrow(GameState::clearBuilds);

        this.board1 = new char[][]{
                {'G', 'G', '_'},
                {'B', 'R', '_'},
                {'B', 'R', '_'}};

        this.board2 = new char[][]{
                {'G', 'R', 'X'},
                {'G', 'R', '_'},
                {'B', 'B', 'X'}};

        assertDoesNotThrow(() -> this.state1 = new GameState(this.board1));
        assertDoesNotThrow(() -> this.state2 = new GameState(this.board2));
    }

    @Test
    void getBuilds()
    {
        assertEquals(2, GameState.getBuilds());
    }

    @Test
    void clearBuilds()
    {
        assertDoesNotThrow(GameState::clearBuilds);
        assertEquals(0, GameState.getBuilds());
    }

    @Test
    void getCell()
    {
        for (int row = 0; row < GameState.size; row++)
            for (int col = 0; col < GameState.size; col++)
                assertEquals(this.board1[row][col], this.state1.getCell(row, col));

        for (int row = 0; row < GameState.size; row++)
            for (int col = 0; col < GameState.size; col++)
                assertEquals(this.board2[row][col], this.state2.getCell(row, col));
    }

    @Test
    void getCost()
    {
        assertEquals(0, state1.getCost());
        assertEquals(0, state2.getCost());
    }

    @Test
    void getPrevious()
    {
        assertNull(state1.getPrevious());
        assertNull(state2.getPrevious());
    }

    @Test
    void setCost()
    {
        assertDoesNotThrow(() -> state1.setCost(1));
        assertDoesNotThrow(() -> state2.setCost(2));
        assertEquals(1, state1.getCost());
        assertEquals(2, state2.getCost());
    }

    @Test
    void setPrevious()
    {
        assertDoesNotThrow(() -> state1.setPrevious(state2));
        assertDoesNotThrow(() -> state2.setPrevious(state1));
        assertEquals(state2, state1.getPrevious());
        assertEquals(state1, state2.getPrevious());
    }

    @Test
    void testHashCode()
    {
        assertEquals(Arrays.deepHashCode(this.board1), this.state1.hashCode());
        assertEquals(Arrays.deepHashCode(this.board2), this.state2.hashCode());
    }

    @Test
    void testEquals()
    {
        GameState clone1 = new GameState(this.board1);
        GameState clone2 = new GameState(this.board2);

        assertNotSame(this.state1, clone1);
        assertNotSame(this.state2, clone2);

        assertEquals(this.state1, clone1);
        assertEquals(this.state2, clone2);
    }

    @Test
    void testToString()
    {
        assertEquals("""
                [G, G, _]
                [B, R, _]
                [B, R, _]""", state1.toString());

        assertEquals("""
                [G, R, X]
                [G, R, _]
                [B, B, X]""", state2.toString());
    }

    @Test
    void neighbors()
    {
        char[][][] neighbours1 = new char[][][]
                {

                {{'_', 'G', 'G'},
                 {'B', 'R', '_'},
                 {'B', 'R', '_'}},

                {{'G', '_', 'G'},
                 {'B', 'R', '_'},
                 {'B', 'R', '_'}},

                {{'G', 'G', '_'},
                 {'_', 'R', 'B'},
                 {'B', 'R', '_'}},

                {{'G', 'G', '_'},
                 {'B', '_', 'R'},
                 {'B', 'R', '_'}},

                {{'G', 'G', '_'},
                 {'B', 'R', '_'},
                 {'_', 'R', 'B'}},

                {{'G', 'G', '_'},
                 {'B', 'R', '_'},
                 {'B', '_', 'R'}}

                };

        char[][][] neighbours2 = new char[][][]
                {

                {{'G', 'R', 'X'},
                 {'_', 'R', 'G'},
                 {'B', 'B', 'X'}},

                {{'G', 'R', 'X'},
                 {'G', '_', 'R'},
                 {'B', 'B', 'X'}}

                };


        int[] costs1 = {3, 3, 1, 10, 1, 10};
        int[] costs2 = {3, 10};

        int ind = 0;
        for (Node neighbour : this.state1.neighbors())
        {
            if (neighbour == null) break;
            GameState expected = new GameState(neighbours1[ind]);
            assertEquals(expected, neighbour);
            assertEquals(this.state1, neighbour.getPrevious());
            assertEquals(costs1[ind], neighbour.getCost());
            ind++;
        }

        ind = 0;
        for (Node neighbour : this.state2.neighbors())
        {
            if (neighbour == null) break;
            System.out.println(neighbour);
            GameState expected = new GameState(neighbours2[ind]);
            assertEquals(expected, neighbour);
            assertEquals(this.state2, neighbour.getPrevious());
            assertEquals(costs2[ind], neighbour.getCost());
            ind++;
        }
    }
}