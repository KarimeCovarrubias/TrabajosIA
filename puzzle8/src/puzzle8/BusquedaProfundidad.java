package puzzle8;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class BusquedaProfundidad {
    private Nodo raiz;
    public int nodosVisitados = 0;

    public BusquedaProfundidad(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo buscar(String estadoObjetivo) {
        HashSet<String> visitados = new HashSet<>();
        Stack<Nodo> pila = new Stack<>();

        pila.push(raiz);
        visitados.add(raiz.estado);

        while (!pila.isEmpty()) {

            Nodo actual = pila.pop();
            nodosVisitados++;

            if (actual.estado.equals(estadoObjetivo)) {
                return actual;
            }

            List<Nodo> sucesores = actual.generarSucesores();

            for (Nodo sucesor : sucesores) {
                if (!visitados.contains(sucesor.estado)) {
                    visitados.add(sucesor.estado);
                    pila.push(sucesor);
                }
            }
        }
        return null;
    }
}