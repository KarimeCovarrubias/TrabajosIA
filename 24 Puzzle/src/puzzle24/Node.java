package puzzle24;

import java.util.ArrayList;

public class Node {
    private boolean visited;
    private String state;
    private ArrayList<Node> children;
    private Node parent;
    private int cost;                    // g(n)
    private int estimatedCostToGoal;     // h(n)
    private int totalCost;               // f(n) = g + h
    private int depth;

    // Constructor
    public Node(String state) {
        this.state = state;
        this.children = new ArrayList<>();
        this.parent = null;
        this.cost = 0;
        this.estimatedCostToGoal = 0;
        this.totalCost = 0;
        this.depth = 0;
        this.visited = false;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public int getCost() {
        return cost;
    }

    public int getEstimatedCostToGoal() {
        return estimatedCostToGoal;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setEstimatedCostToGoal(int estimatedCostToGoal) {
        this.estimatedCostToGoal = estimatedCostToGoal;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    // Usado por A*
    public void setTotalCost(int cost, int estimatedCost) {
        this.cost = cost;
        this.estimatedCostToGoal = estimatedCost;
        this.totalCost = cost + estimatedCost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}