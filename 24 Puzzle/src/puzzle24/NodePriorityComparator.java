package puzzle24;

import java.util.Comparator;

//This is a comparator used by the priorityQueue to store nodes sorted by the cost value
public class NodePriorityComparator implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        if (x.getTotalCost() < y.getTotalCost()) {
            return -1;
        }
        if (x.getTotalCost() > y.getTotalCost()) {
            return 1;
        }
        return 0;
    }
}