package org.example;

public interface Node extends Comparable<Node>
{
    int getCost();
    Node getPrevious();
    void setCost(int cost);
    void setPrevious(Node previous);
    Iterable<Node> neighbors();

    @Override
    default int compareTo(Node other)
    {
        return Integer.compare(this.getCost(), other.getCost());
    }
}
