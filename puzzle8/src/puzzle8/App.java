package puzzle8;

import java.util.LinkedList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String inicial = "1238 4765";
        String objetivo = "1284376 5";
        Nodo nodoInicial = new Nodo(inicial);

        // ------------------ BFS ------------------
        BusquedaAnchura bfs = new BusquedaAnchura(nodoInicial);
        long inicioBFS = System.currentTimeMillis();
        Nodo solucionBFS = bfs.buscar(objetivo);
        long finBFS = System.currentTimeMillis();

        // ------------------ DFS ------------------
        BusquedaProfundidad dfs = new BusquedaProfundidad(new Nodo(inicial));
        long inicioDFS = System.currentTimeMillis();
        Nodo solucionDFS = dfs.buscar(objetivo);
        long finDFS = System.currentTimeMillis();

        System.out.println("========== RESULTADOS ==========");

        if (solucionBFS != null) {
            System.out.println("Solución BFS");
            System.out.println("Nivel: " + solucionBFS.getNivel());
            System.out.println("Nodos visitados: " + bfs.nodosVisitados);
            System.out.println("Tiempo: " + (finBFS - inicioBFS) + " ms");
        }

        if (solucionDFS != null) {
            System.out.println("\nSolución DFS");
            System.out.println("Nivel: " + solucionDFS.getNivel());
            System.out.println("Nodos visitados: " + dfs.nodosVisitados);
            System.out.println("Tiempo: " + (finDFS - inicioDFS) + " ms");
        }
    }
}