package arbol;

public class App {
    public static void main(String[] args) {
        ArbolBinario<Integer> arbol = new ArbolBinario<>();

        arbol.insertar(10);
        arbol.insertar(5);
        arbol.insertar(20);
        arbol.insertar(3);
        arbol.insertar(8);

        System.out.println("Recorrido inorden:");
        arbol.imprimir();
    }
}
