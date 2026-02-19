package puzzle8;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BusquedaAnchura {
    private Nodo raiz;
    public int nodosVisitados = 0;

    public BusquedaAnchura(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo buscar(String estadoObjetivo) {

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);

        while (!cola.isEmpty()) {

            Nodo actual = cola.poll();
            nodosVisitados++;

            if (actual.estado.equals(estadoObjetivo)) {
                return actual;
            }

            List<Nodo> sucesores = actual.generarSucesores();

            for (Nodo sucesor : sucesores) {
                if (!visitados.contains(sucesor.estado)) {
                    visitados.add(sucesor.estado);
                    cola.add(sucesor);
                }
            }
        }
        return null;
    }
}