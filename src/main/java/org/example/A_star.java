package org.example;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.function.BiFunction;




public class A_star implements Algorithm
{
    @Override
    public Node run(Node start, Node goal, boolean verbose, BiFunction<Node, Node, Integer> NewCost)
    {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Node> expanded = new HashSet<>(), expanding = new HashSet<>();
        queue.add(start); expanding.add(start);

        while (! queue.isEmpty())
        {
            if (verbose) System.out.println(expanding.size());

            Node next = queue.poll();
            expanding.remove(next);
            expanded.add(next);

            if (next.equals(goal)) return next;

            for (Node neighbour : next.neighbors())
            {
                if (neighbour == null) continue;

                neighbour.setCost(NewCost.apply(next, neighbour));

                if (expanded.contains(neighbour) || expanding.contains((neighbour)))
                    if (neighbour.compareTo(next) >= 0) continue;

                queue.add(neighbour); expanding.add(neighbour);
            }
        }

        return null;
    }
}