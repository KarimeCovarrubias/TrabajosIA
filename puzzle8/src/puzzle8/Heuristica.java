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

    public static int conflictoLineal(String estado, String objetivo) {
        int manhattan = 0;
        int conflictos = 0;

        // Distancia Manhattan
        for (int i = 0; i < estado.length(); i++) {
            char valor = estado.charAt(i);

            if (valor == ' ') {
                continue;
            }

            int indiceObjetivo = objetivo.indexOf(valor);

            int filaActual = i / 3;
            int colActual = i % 3;

            int filaObjetivo = indiceObjetivo / 3;
            int colObjetivo = indiceObjetivo % 3;

            manhattan += Math.abs(filaActual - filaObjetivo) + Math.abs(colActual - colObjetivo);
        }

        // Conflictos en filas
        for (int fila = 0; fila < 3; fila++) {
            for (int col1 = 0; col1 < 3; col1++) {
                for (int col2 = col1 + 1; col2 < 3; col2++) {

                    int i = fila * 3 + col1;
                    int j = fila * 3 + col2;

                    char t1 = estado.charAt(i);
                    char t2 = estado.charAt(j);

                    if (t1 == ' ' || t2 == ' ') continue;

                    int pos1 = objetivo.indexOf(t1);
                    int pos2 = objetivo.indexOf(t2);

                    if (pos1 / 3 == fila && pos2 / 3 == fila) {
                        if (pos1 > pos2) {
                            conflictos++;
                        }
                    }
                }
            }
        }

        // Conflictos en columnas
        for (int col = 0; col < 3; col++) {
            for (int fila1 = 0; fila1 < 3; fila1++) {
                for (int fila2 = fila1 + 1; fila2 < 3; fila2++) {
                    int i = fila1 * 3 + col;
                    int j = fila2 * 3 + col;
                    char t1 = estado.charAt(i);
                    char t2 = estado.charAt(j);

                    if (t1 == ' ' || t2 == ' ') continue;

                    int pos1 = objetivo.indexOf(t1);
                    int pos2 = objetivo.indexOf(t2);

                    if (pos1 % 3 == col && pos2 % 3 == col) {
                        if (pos1 > pos2) {
                            conflictos++;
                        }
                    }
                }
            }
        }
        return manhattan + (2 * conflictos);
    }
}