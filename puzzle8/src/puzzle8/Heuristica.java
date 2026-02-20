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

    private int heuristicTwo(String currentState, String goalState) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1) {
            for (int j = 0; j < goalState.length(); j += 1) {
                if (currentState.charAt(i) == goalState.charAt(j)) {
                    difference = difference + ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j /3));
                }
                return difference;
            }
        }
        return difference;
    }

    public static double distanciaEuclidiana(Nodo n, Nodo objetivo) {
        return 0;
    }
}