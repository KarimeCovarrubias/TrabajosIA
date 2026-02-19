package puzzle8;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolBusqueda {
    private Nodo raiz;

    public ArbolBusqueda(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo busquedaAnchura(String estadoObjetivo) {
        if (raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            if (actual.estado.equals(estadoObjetivo)) {
                return actual;
            }

            List<Nodo> sucesores = actual.generarSucesores();
            for (int i = 0; i < sucesores.size(); i++) {
                Nodo sucesor = sucesores.get(i);
                if (!visitados.contains(sucesor.estado)) {
                    visitados.add(sucesor.estado);
                    cola.add(sucesor);
                }
            }
        }
        return null;
    }
}