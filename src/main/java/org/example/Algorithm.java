package org.example;

import java.util.function.BiFunction;

public interface Algorithm
{
    Node run(
            Node start,
            Node goal,
            boolean verbose,
            BiFunction<Node, Node, Integer> NewCost
    );
}
