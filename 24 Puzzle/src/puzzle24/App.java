package puzzle24;
import java.util.*;

public class App {
    final static private String GOAL_STATE = "123456789ABCDEFGHIJKLMNO0";

    public static void main(String[] args) {
        String rootState = generateRandomSolvable();

        System.out.println("Estado inicial: " + rootState);

        long startTime = System.currentTimeMillis();

        SearchTree search = new SearchTree(new Node(rootState), GOAL_STATE);
        search.aStar(Heuristic.H_TWO);

        long finishTime = System.currentTimeMillis();
        System.out.println("Time  :" + (finishTime - startTime));
    }

    // Generar estado aleatorio resoluble
    private static String generateRandomSolvable() {
        List<Character> tiles = new ArrayList<>();

        for (char c : GOAL_STATE.toCharArray()) {
            tiles.add(c);
        }

        Random random = new Random();

        while (true) {
            Collections.shuffle(tiles, random);
            StringBuilder sb = new StringBuilder();

            for (char c : tiles) {
                sb.append(c);
            }

            String candidate = sb.toString();

            if (isSolvable(candidate)) {
                return candidate;
            }
        }
    }

    // Verifica si es resoluble (tablero impar → solo inversiones)
    private static boolean isSolvable(String state) {
        int inversions = 0;

        for (int i = 0; i < state.length(); i++) {
            for (int j = i + 1; j < state.length(); j++) {
                char a = state.charAt(i);
                char b = state.charAt(j);

                if (a == '0' || b == '0') continue;

                if (Character.getNumericValue(a) > Character.getNumericValue(b)) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }
}