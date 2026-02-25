package puzzle24;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class NodeUtil {
    public static List<String> getSuccessors(String state) {
        List<String> successors = new ArrayList<>();
        int size = (int) Math.sqrt(state.length()); // 5 para 24 puzzle
        int zeroPos = state.indexOf('0');
        int row = zeroPos / size;
        int col = zeroPos % size;

        // ARRIBA
        if (row > 0) {
            successors.add(swap(state, zeroPos, zeroPos - size));
        }

        // ABAJO
        if (row < size - 1) {
            successors.add(swap(state, zeroPos, zeroPos + size));
        }

        // IZQUIERDA
        if (col > 0) {
            successors.add(swap(state, zeroPos, zeroPos - 1));
        }

        // DERECHA
        if (col < size - 1) {
            successors.add(swap(state, zeroPos, zeroPos + 1));
        }

        return successors;
    }

    private static String swap(String state, int i, int j) {
        char[] arr = state.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }

    public static void printSolution(Node goalNode, Set<String> visitedNodes, Node root, int time) {
        int totalCost = 0;
        Stack<Node> solutionStack = new Stack<>();
        solutionStack.push(goalNode);

        while (!goalNode.getState().equals(root.getState())) {
            goalNode = goalNode.getParent();
            solutionStack.push(goalNode);
        }

        String sourceState = root.getState();
        String destinationState;
        int cost = 0;

        while (!solutionStack.isEmpty()) {

            Node current = solutionStack.pop();
            destinationState = current.getState();

            if (!sourceState.equals(destinationState)) {

                int movedIndex = sourceState.indexOf('0');
                char movedTile = destinationState.charAt(movedIndex);

                System.out.println("Move " + movedTile + " "
                        + findTransition(sourceState, destinationState));

                cost = Character.getNumericValue(movedTile);
                totalCost += cost;
            }
            sourceState = destinationState;

            printBoard(destinationState);

            System.out.println("Cost of movement: " + cost);
            System.out.println("-----------------------------------");
        }

        System.out.println("Transitions: " + (solutionStack.size()));
        System.out.println("Visited states: " + visitedNodes.size());
        System.out.println("Total cost: " + totalCost);
        System.out.println("Nodes expanded: " + time);
    }

    private static void printBoard(String state) {
        int size = (int) Math.sqrt(state.length());

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.print(state.charAt(r * size + c) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static MovementType findTransition(String source, String destination) {
        int size = (int) Math.sqrt(source.length());
        int diff = destination.indexOf('0') - source.indexOf('0');

        if (diff == -size) return MovementType.DOWN;
        if (diff == size) return MovementType.UP;
        if (diff == 1) return MovementType.LEFT;
        if (diff == -1) return MovementType.RIGHT;

        return null;
    }
}