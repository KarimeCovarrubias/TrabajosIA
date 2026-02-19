package puzzle8;

public class Heuristica {
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
}