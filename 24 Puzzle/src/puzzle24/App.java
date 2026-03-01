package puzzle24;
import java.util.*;

public class App {
    static Scanner scanner = new Scanner(System.in);
    final static private String GOAL_STATE = "123456789ABCDEFGHIJKLMNO0";

    public static void main(String[] args) {
        int opc;
        String currentBoard = "";

        do {
            System.out.println("\n------------------------------------------------------------------");
            System.out.println("\n===== 24 PUZZLE - CONFIGURACIÓN =====");
            System.out.println("1. Generar tablero FÁCIL (20 movimientos)");
            System.out.println("2. Generar tablero DIFÍCIL (100 movimientos)");
            System.out.println("3. Resolver con Manhattan");
            System.out.println("4. Resolver con Manhattan + Conflicto Lineal");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opc = scanner.nextInt();
            System.out.println("\n------------------------------------------------------------------");

            switch (opc) {
                case 1:
                    System.out.println("\n------------------------------------------------------------------");
                    currentBoard = generateRandomFromGoal(20);
                    System.out.println("\nTablero FÁCIL generado.");
                    System.out.println("\n------------------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("\n------------------------------------------------------------------");
                    currentBoard = generateRandomFromGoal(100);
                    System.out.println("\nTablero DIFÍCIL generado.");
                    System.out.println("\n------------------------------------------------------------------");
                    break;
                case 3:
                case 4:
                    if (currentBoard.isEmpty()) {
                        System.out.println("\n------------------------------------------------------------------");
                        System.out.println("Error. Primero genera un tablero (opción 1 o 2).");
                        System.out.println("\n------------------------------------------------------------------");
                    } else {
                        switch (opc) {
                            case 3:
                                ejecutarBusqueda(currentBoard, Heuristic.H_TWO, "Manhattan");
                                break;
                            case 4:
                                ejecutarBusqueda(currentBoard, Heuristic.H_FOUR, "Conflicto lineal");
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("Introduzca una opción correcta.");
                    break;
            }
        } while (opc != 5);
    }

    private static void ejecutarBusqueda(String estado, Heuristic h, String heruristica) {
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("Estado inicial: " + estado);
        System.out.println("Heurística: " + heruristica);
        
        long startTime = System.currentTimeMillis();
        SearchTree search = new SearchTree(new Node(estado), GOAL_STATE);
        search.idaStar(h);
        long finishTime = System.currentTimeMillis();
        
        System.out.println("Tiempo total: " + (finishTime - startTime) + " ms");
        System.out.println("------------------------------------------------------------------\n");
    }
    
    private static String generateRandomFromGoal(int moves) {
        String current = GOAL_STATE;
        Random random = new Random();
        for (int i = 0; i < moves; i++) {
            List<String> successors = NodeUtil.getSuccessors(current);
            current = successors.get(random.nextInt(successors.size()));
        }
        return current;
    }
}