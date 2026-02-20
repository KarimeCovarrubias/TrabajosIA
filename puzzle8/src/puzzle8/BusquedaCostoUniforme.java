package puzzle8;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class BusquedaCostoUniforme {

    private Nodo raiz;
    public int nodosVisitados = 0;

    public BusquedaCostoUniforme(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo buscar(String estadoObjetivo) {

        HashSet<String> visitados = new HashSet<>();

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(
                Comparator.comparingInt(Nodo::getCosto)
        );

        raiz.setCosto(0);
        colaPrioridad.add(raiz);

        while (!colaPrioridad.isEmpty()) {

            Nodo actual = colaPrioridad.poll();
            nodosVisitados++;

            if (actual.estado.equals(estadoObjetivo)) {
                return actual;
            }

            if (!visitados.contains(actual.estado)) {

                visitados.add(actual.estado);

                List<Nodo> sucesores = actual.generarSucesores();

                for (Nodo sucesor : sucesores) {
                    colaPrioridad.add(sucesor);
                }
            }
        }

        return null;
    }
}