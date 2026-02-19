package puzzle8;

import java.util.LinkedList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Nodo nodo = new Nodo("1238 4765"); 
        ArbolBusqueda arbol = new ArbolBusqueda(nodo);
        String objetivo = "1284376 5"; 
        Nodo solucion = arbol.busquedaAnchura(objetivo);

        if (solucion == null) {
            System.out.println("No tiene solución.");
            return;
        }
        System.out.println("Estado: " + solucion.estado);
        System.out.println("Nivel de movimientos " + solucion.getNivel());

        List<String> camino = reconstruirCamino(solucion);
        System.out.println("Camino " + (camino.size() - 1) + "\nMovimientos:");
        System.out.println("------");
        for (int i = 0; i < camino.size(); i++) {
            String e = camino.get(i);
            imprimir(e);
            System.out.println("------");
        }
    }

    public static List<String> reconstruirCamino(Nodo n) {
        LinkedList<String> camino = new LinkedList<>();
        while (n != null) {
            camino.addFirst(n.estado);
            n = n.padre;
        }
        return camino;
    }

    public static void imprimir(String e) {
        System.out.println(e.substring(0, 3));
        System.out.println(e.substring(3, 6));
        System.out.println(e.substring(6, 9));
    }
}