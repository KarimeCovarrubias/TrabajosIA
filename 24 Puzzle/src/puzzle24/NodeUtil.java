package puzzle24;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by HABDOLLA on 1/15/2016.
 * Modified by Mario Rios on 2026-02-19.
 * This is a utility class that can do some operations on a node. for example getSuccessors() return the successors of a node
 */
public class NodeUtil {
    
    /**
     * Generates all valid successor states for the 8-puzzle game.
     * 
     * The empty position (0) can swap with adjacent positions based on grid layout.
     * Uses an adjacency map to determine valid moves for each position.
     * 
     * @param state a 9-character string representing puzzle positions (0-8 left-to-right, top-to-bottom).
     *              Character '0' represents the empty space.
     * @return a list of all possible successor states reachable in one move
     * 
     * Example with state "023814765":
     *   Grid layout:      |0|2|3|
     *                     |8|1|4|
     *                     |7|6|5|
     *   
     *   Valid moves (swapping 0 with adjacent positions):
     *                     |2|0|3|    |8|2|3|
     *                     |8|1|4|    |0|1|4|
     *                     |7|6|5|    |7|6|5|
     *   
     *   Return: ["203814765", "823014765"]
     */
    public static List<String> getSuccessors(String state) {
        List<String> successors = new ArrayList<>();
        int zeroPos = state.indexOf("0");
        int row = zeroPos / 5;
        int col = zeroPos % 5;
        
        // Adjacency map: for each position, which positions can it swap with
        int[][] directions = {
                {-1, 0}, // arriba
                {1, 0},  // abajo
                {0, -1}, // izquierda
                {0, 1}   // derecha
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < 5 && newCol >= 0 && newCol < 5) {
                int newIndex = newRow * 5 + newCol;
                successors.add(swapPositions(state, zeroPos, newIndex));
            }
        }
        
        return successors;
    }
    
    /**
     * Swaps characters at two positions in a string.
     * @param state the original string
     * @param pos1 first position
     * @param pos2 second position
     * @return new string with swapped positions
     */
    private static String swapPositions(String state, int pos1, int pos2) {
        char[] arr = state.toCharArray();
        char temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return new String(arr);
    }

    /**
     *
     *  prints the transitions along with the states starting from the initial states
     *  to the goal state.
     *
     * @author Himan Abdollahpouri

     */
    // NECESITO LLAMAR AQUI PRINTBOARD
    public static void printSolution(Node goalNode, Set<String> visitedNodes, Node root, int time) {
        int totalCost = 0;

        Stack<Node> solutionStack = new Stack<>();
        solutionStack.push(goalNode);
        while (!goalNode.getState().equals(root.getState())) {
            solutionStack.push(goalNode.getParent());
            goalNode = goalNode.getParent();
        }

        String sourceState = root.getState();
        int cost = 0;

        for (int i = solutionStack.size() - 1; i >= 0; i--) {
            Node currentNode = solutionStack.get(i);
            String destinationState = currentNode.getState();

            System.out.println("-----------------------------------");
            if (!sourceState.equals(destinationState)) {
                System.out.println("Move " + destinationState.charAt(sourceState.indexOf('0')) + " "
                        + findTransition(sourceState, destinationState));
                cost = Character.getNumericValue(destinationState.charAt(sourceState.indexOf('0')));
                totalCost += cost;
            }

            System.out.println("Cost of movement: " + cost);
            System.out.println("Tablero actual:");
            printBoard(destinationState);

            sourceState = destinationState;
        }

        System.out.println("-----------------------------------");
        System.out.println("** Number of transitions: " + (solutionStack.size() - 1));
        System.out.println("** Number of visited states: " + (visitedNodes == null ? 0 : visitedNodes.size()));
        System.out.println("** Total cost: " + totalCost);
        System.out.println("** Number of Nodes popped: " + time);
    }

//*******************************************************************************************
    /**
     *
     * @return returns the transition between two states.
     *
     * @author Himan Abdollahpouri

     */
    public static MovementType findTransition(String source, String destination) {
        int zero_position_difference = destination.indexOf('0') - source.indexOf('0');
        switch (zero_position_difference) {
            case -5:
                return MovementType.DOWN;
            case 5:
                return MovementType.UP;
            case 1:
                return MovementType.LEFT;
            case -1:
                return MovementType.RIGHT;
        }
        return null;
    }

    private static void printBoard(String state) {
        int size = (int) Math.sqrt(state.length());
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                char tile = state.charAt(r * size + c);
                System.out.printf("%2s ", (tile == '0' ? "." : tile));
            }
            System.out.println();
        }
        System.out.println();
    }
}