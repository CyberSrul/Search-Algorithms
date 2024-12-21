package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.BiFunction;




public class IDAstar implements Algorithm
{
    @Override
    public Node run(Node start, Node goal, boolean verbose, BiFunction<Node, Node, Integer> NewCost)
    {
        goal.setCost(Integer.MAX_VALUE);

        for (int threshold = NewCost.apply(goal, start), update; true; threshold = update)
        {
            Stack<Node> stack = new Stack<>();
            HashSet<Node> expanded = new HashSet<>();
            HashMap<Node, Integer> expanding = new HashMap<>();
            stack.push(start);
            expanding.put(start, start.getCost());

            for (update = Integer.MAX_VALUE; ! stack.isEmpty(); )
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

                for (Node neighbour : next.neighbors())
                {
                    if (neighbour == null) continue;
                    if (expanded.contains(neighbour)) continue;

                    neighbour.setCost(NewCost.apply(next, neighbour));

                    if (neighbour.getCost() > threshold)
                    {
                        if (update > neighbour.getCost()) update = neighbour.getCost();
                        continue;
                    }

                    if (neighbour.equals(goal))
                        if (neighbour.getCost() < goal.getCost())
                            goal = neighbour;

                    if (expanding.containsKey(neighbour))
                        if (neighbour.getCost() >= expanding.get(neighbour)) continue;


                    stack.push(neighbour);
                    expanding.put(neighbour, neighbour.getCost());
                }
            }

            if (goal.getPrevious() != null) return goal;
        }
    }
}