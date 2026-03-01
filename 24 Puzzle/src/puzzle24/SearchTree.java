package puzzle24;

import java.util.*;

public class SearchTree {
    private Node root;
    private String goalSate;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getGoalSate() {
        return goalSate;
    }

    public void setGoalSate(String goalSate) {
        this.goalSate = goalSate;
    }

    public SearchTree(Node root, String goalSate) {
        this.root = root;
        this.goalSate = goalSate;
    }

    //******************************************************************************************************
    //    breadthFirstSearch() find the goal state using Breadth First Search algorithm
    //    we start with the initial state. check if it is goal or not and expand its children if it is not the goal.
    //    pop the first element of the queue and check if it is goal node. if not add its children to the queue.
    //    repeat the process untill the solution is found
    public void breadthFirstSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        Queue<Node> queue = new LinkedList<Node>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                queue.add(child);

            }
            currentNode = queue.poll();
            time += 1;
        }

        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }

    //**********************************************************************************************
    public void depthFirstSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        //the queue that store nodes that we should expand
        MyQueue<Node> mainQueue = new MyQueue<>();
        //the queue that contains the successors of the expanded node
        MyQueue<Node> successorsQueue = new MyQueue<>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                successorsQueue.enqueue(child);

            }
            //we add the queue that contains the successors of the visted node to the beginning of the main queue
            mainQueue.addQueue(successorsQueue);
            //successors queue should be cleared in order to store the next expaneded's successors
            successorsQueue.clear();
            currentNode = mainQueue.dequeue();
            time += 1;
            nodeSuccessors.clear();
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }

    /**
     * Find the goal using Uniform cost algorithm. in each step the node with minimum cost will be expanded
     */
    //***************************************************************************************************
    public void uniformCostSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        node.setCost(0);
        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();
        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = 
            new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState()); // Visitados
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), 0);
                nodePriorityQueue.add(child);
            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }

    /**
     * Find the goal using Best Search First algorithm. The heuristic is
     * the estimated cost from the current node to the goal node
     */
    //*****************************************************************************************
    public void bestFirstSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        node.setCost(0);

        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();

        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);

                child.setTotalCost(0, heuristicOne(child.getState(), goalSate));
                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        // Here we try to navigate from the goal node to its parent( and its parent's parent and so on) to find the path
        NodeUtil.printSolution(currentNode, stateSets, root, time);
    }
    
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Node solutionNode;
    private int nodesExpanded = 0;

    public void idaStar(Heuristic heuristic) {
        int threshold = calculateHeuristic(root.getState(), heuristic);
        nodesExpanded = 0; // Reiniciar contador
        solutionNode = null;
        
        while (true) {
            int temp = search(root, 0, threshold, heuristic);

            if (temp == -1) { // Solución encontrada
                //System.out.println("Solución encontrada.");
                // Pasamos 'nodesExpanded' como el parámetro 'time'
                NodeUtil.printSolution(solutionNode, null, root, nodesExpanded);
                return;
            }

            if (temp == Integer.MAX_VALUE) {
                System.out.println("No se encontró solución.");
                return;
            }
            threshold = temp; // Actualizar el límite con el valor mínimo que excedió
        }
    }

    private int search(Node node, int g, int threshold, Heuristic heuristic) {
        int h = calculateHeuristic(node.getState(), heuristic);
        int f = g + h;

        // Poda por umbral
        if (f > threshold) return f;

        // Condición de éxito
        if (node.getState().equals(goalSate)) {
            solutionNode = node;
            return -1;
        }
        nodesExpanded++;

        int min = Integer.MAX_VALUE;
        List<String> successors = NodeUtil.getSuccessors(node.getState());

        for (String s : successors) {
            if (node.getParent() != null && s.equals(node.getParent().getState())) {
                continue;
            }
            Node child = new Node(s);
            child.setParent(node);

            // En IDA*, el costo de paso (g) suele ser 1 por movimiento
            int temp = search(child, g + 1, threshold, heuristic);

            if (temp == -1) return -1;

            if (temp < min) min = temp;
        }
        return min;
    }
    
    private int calculateHeuristic(String state, Heuristic heuristic) {
        if (heuristic == Heuristic.H_TWO)
            return heuristicTwo(state, goalSate);

        else if (heuristic == Heuristic.H_FOUR)
            return heuristicFour(state, goalSate);

        return 0;
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Find the goal using A* algorithm. The huristic is the real cost to the current node from the initial Node
     * plus the estimated cost from the current node to the goal node
     */
    //*************************************************************************************************
    public void aStar(Heuristic heuristic) {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        node.setTotalCost(0);

        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();

        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);

                if (heuristic == Heuristic.H_ONE)
                    child.setTotalCost(
                        currentNode.getTotalCost() + 0,
                        //Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), 
                        heuristicOne(child.getState(), goalSate)
                        );
                else if (heuristic == Heuristic.H_TWO)
                    child.setTotalCost(currentNode.getTotalCost() + 
                    Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0')))
                    , heuristicTwo(child.getState(), goalSate));
                else if (heuristic == Heuristic.H_THREE)
                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), heuristicThree(child.getState(), goalSate));
                else if (heuristic == Heuristic.H_FOUR)
                    child.setTotalCost(
                        currentNode.getTotalCost() + 
                        Character.getNumericValue(
                            child.getState().charAt(
                                child.getParent().getState().indexOf('0')
                            )
                        ),
                        heuristicFour(child.getState(), goalSate)
                    );
                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);
    }

    /**
     * Search for the goal state using limited depth First Search algorithm
     * the depth limit starts from 1 and it increases to the defiend value by user that passed to the method
     */
    //*****************************************************************************************************
    public void iterativeDeepening(int depthLimit) {
        Node currentNode = new Node(root.getState());
        boolean solutionFound = false;
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        Set<String> totalVisitedStates = new HashSet<>();
        int time = 0;
        for (int maxDepth = 1; maxDepth < depthLimit; maxDepth++) {
            //we should clear the visited list in each iteration
            stateSets.clear();
            //the queue that store nodes that we should expand
            MyQueue<Node> mainQueue = new MyQueue<>();
            //the queue that stores the successors of the expanded node
            MyQueue<Node> successorsQueue = new MyQueue<>();
            Node node = new Node(root.getState());
            mainQueue.enqueue(node);
            currentNode = node;
            List<String> nodeSuccessors = null;
            stateSets.add(currentNode.getState());
            while (!mainQueue.isEmpty()) {
                currentNode = mainQueue.dequeue();
                time += 1;
                if (currentNode.getState().equals(goalSate)) {
                    solutionFound = true;
                    break;
                }
                if (currentNode.getDepth() < maxDepth) {
                    nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
                    for (String n : nodeSuccessors) {
                        if (stateSets.contains(n))
                            continue;

                        stateSets.add(n);
                        Node child = new Node(n);
                        currentNode.addChild(child);
                        child.setParent(currentNode);
                        child.setVisited(true);
                        child.setDepth(currentNode.getDepth() + 1);
                        successorsQueue.enqueue(child);
                    }
                    //we add the queue that contains the successors of the visted node to the beginning of the main queue
                    mainQueue.addQueue(successorsQueue);
                    //successors queue should be cleared in order to store the next expaneded's successors
                    successorsQueue.clear();
                }
            }

            if (solutionFound)
                break;
            totalVisitedStates.addAll(stateSets);
        }
        if (!solutionFound)
            System.out.println("No solution Found!");
        else {
            NodeUtil.printSolution(currentNode, totalVisitedStates, root, time);
        }
    }

    //****************************************************************************************************
    // This heuristic estimates the cost to the goal from current state 
    // by counting the number of misplaced tiles
    // Contar cuantas veces se equivoco.
    private int heuristicOne(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            if (currentState.charAt(i) != goalSate.charAt(i))
                difference += 1;
        return difference;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //*************************************************************************************************
    // This heuristic estimates the cost to the goal from current state by calculating the 
    // Manhathan distance from each misplaced
    // tile to its right position in the goal state
    private int heuristicTwo(String currentState, String goalSate) {
        int difference = 0;
        int size = (int) Math.sqrt(currentState.length());

        for (int i = 0; i < currentState.length(); i++) {
            char currentChar = currentState.charAt(i);

            if (currentChar == '0') continue;
            for (int j = 0; j < goalSate.length(); j++) {
                if (currentChar == goalSate.charAt(j)) {
                    int currentRow = i / size;
                    int currentCol = i % size;

                    int goalRow = j / size;
                    int goalCol = j % size;

                    difference += Math.abs(currentRow - goalRow) + Math.abs(currentCol - goalCol);
                    break;
                }
            }
        }
        return difference;
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //***************************************************************************************************
    // This heurisic is almost similar to heuristicTwo except that heuristicThree has higher value. 
    // when we estimate the cost
    // by calculating the manhattan distance , we underestimate this cost because we do not count to the
    //  number moves the intermediate tiles shoud move
    // in order to make space for the source tile to move. therefore, we think if the manhattan distance is, 
    // let say, 4, it means
    // there are 3 tiles in between that they should also move. so the estimate is 
    // (manhattan distance +manhattan distance-1=
    //2*manhattan distance-1
    private int heuristicThree(String currentState, String goalSate) {
        int difference = 0;
        int size = (int) Math.sqrt(currentState.length());

        for (int i = 0; i < currentState.length(); i++) {
            char currentChar = currentState.charAt(i);

            if (currentChar == '0') continue;

            for (int j = 0; j < goalSate.length(); j++) {
                if (currentChar == goalSate.charAt(j)) {
                    int currentRow = i / size;
                    int currentCol = i % size;

                    int goalRow = j / size;
                    int goalCol = j % size;

                    int manhattan = Math.abs(currentRow - goalRow) + Math.abs(currentCol - goalCol);

                    difference += (2 * manhattan - 1);
                    break;
                }
            }
        }
        return difference;
    }
    
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private int linearConflict(String currentState, String goalState) {
        int size = (int) Math.sqrt(currentState.length());
        int conflict = 0;

        // Revisar conflictos en filas
        for (int row = 0; row < size; row++) {
            for (int col1 = 0; col1 < size; col1++) {
                int index1 = row * size + col1;
                char tile1 = currentState.charAt(index1);

                if (tile1 == '0') continue;

                int goalIndex1 = goalState.indexOf(tile1);
                int goalRow1 = goalIndex1 / size;

                if (goalRow1 != row) continue;

                for (int col2 = col1 + 1; col2 < size; col2++) {
                    int index2 = row * size + col2;
                    char tile2 = currentState.charAt(index2);

                    if (tile2 == '0') continue;

                    int goalIndex2 = goalState.indexOf(tile2);
                    int goalRow2 = goalIndex2 / size;

                    if (goalRow2 != row) continue;

                    if (goalIndex1 > goalIndex2) {
                        conflict += 2;
                    }
                }
            }
        }

        // Revisar conflictos en columnas
        for (int col = 0; col < size; col++) {
            for (int row1 = 0; row1 < size; row1++) {
                int index1 = row1 * size + col;
                char tile1 = currentState.charAt(index1);

                if (tile1 == '0') continue;

                int goalIndex1 = goalState.indexOf(tile1);
                int goalCol1 = goalIndex1 % size;

                if (goalCol1 != col) continue;

                for (int row2 = row1 + 1; row2 < size; row2++) {
                    int index2 = row2 * size + col;
                    char tile2 = currentState.charAt(index2);

                    if (tile2 == '0') continue;

                    int goalIndex2 = goalState.indexOf(tile2);
                    int goalCol2 = goalIndex2 % size;

                    if (goalCol2 != col) continue;

                    if (goalIndex1 > goalIndex2) {
                        conflict += 2;
                    }
                }
            }
        }
        return conflict;
    }
    
    private int heuristicFour(String currentState, String goalState) {
        return heuristicTwo(currentState, goalState) 
               + linearConflict(currentState, goalState);
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}