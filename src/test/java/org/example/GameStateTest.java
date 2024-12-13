package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest
{
    GameState state1, state2;

    @BeforeEach
    void setUp()
    {
        this.state1 = new GameState(new char[][]{
                {'G', 'G', '_'},
                {'B', 'R', '_'},
                {'B', 'R', '_'}});

        this.state2 = new GameState(new char[][]{
                {'G', 'R', 'X'},
                {'G', 'R', '_'},
                {'B', 'B', 'X'}});
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