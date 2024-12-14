package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex1Test {

    @Test
    void heuristic()
    {

        GameState state1 = new GameState(new char[][]{
                {'G', 'G', '_'},
                {'B', 'R', '_'},
                {'B', 'R', '_'}});

        GameState goal1 = new GameState(new char[][]{
                {'G', '_', 'G'},
                {'R', 'B', '_'},
                {'B', '_', 'R'}});

        GameState state2 = new GameState(new char[][]{
                {'G', 'R', 'X'},
                {'G', 'R', '_'},
                {'B', 'B', 'X'}});

        GameState goal2 = new GameState(new char[][]{
                {'G', 'R', 'X'},
                {'B', 'R', 'G'},
                {'_', 'B', 'X'}});

        assertEquals(24, Ex1.heuristic(state1, goal1));
        assertEquals(4, Ex1.heuristic(state2, goal2));
    }
}