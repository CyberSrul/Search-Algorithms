package org.example;

import java.util.*;
import java.util.function.BiFunction;




public class BnB implements Algorithm
{
    @Override
    public Node run(Node start, Node goal, boolean verbose, BiFunction<Node, Node, Integer> NewCost)
    {
        Stack<Node> stack = new Stack<>();
        HashSet<Node> expanded = new HashSet<>();
        HashMap<Node, Integer> expanding = new HashMap<>();
        stack.push(start); expanding.put(start, start.getCost());

        for (int threshold = Integer.MAX_VALUE; ! stack.isEmpty(); )
        {
            if (verbose) System.out.println(expanding.size());

            Node next = stack.peek();
            if (expanded.contains(next) || ! expanding.containsKey(next))
            {
                stack.pop();
                expanded.remove(next);
                continue;
            }
            expanding.remove(next);
            expanded.add(next);

            PriorityQueue<Node> neighbourhood = new PriorityQueue<>(Comparator.reverseOrder());
            for (Node neighbour : next.neighbors())
            {
                if (neighbour == null) continue;
                if (expanded.contains(neighbour)) continue;

                neighbour.setCost(NewCost.apply(next, neighbour));
                if (neighbour.getCost() >= threshold) continue;

                neighbourhood.add(neighbour);
            }

            while (! neighbourhood.isEmpty())
            {
                Node neighbour = neighbourhood.poll();

                if (neighbour.getCost() >= threshold) break;
                if (neighbour.equals(goal))
                {
                    goal = neighbour;
                    threshold = goal.getCost();
                }

                if (expanding.containsKey(neighbour))
                    if (neighbour.getCost() >= expanding.get(neighbour)) continue;

                stack.push(neighbour);
                expanding.put(neighbour, neighbour.getCost());
            }
        }

        return goal;
    }
}