package puzzle8;
public class Puzzle8 {
    private String estadoInicial = "1238 4765";
    private String estadoObjetivo = "12 843765";

    public static String[] generarSucesores(String estadoActual) {
        String[] sucesores = new String[4];
        int indice = estadoActual.indexOf(" ");

        switch (indice) {
            case 0: 
                sucesores = new String[2];
                sucesores[0] = estadoActual.charAt(1) + " " + estadoActual.substring(2);
                sucesores[1] = estadoActual.charAt(3) + estadoActual.substring(1, 3) + " " + estadoActual.substring(4);
            break;
            case 1:
                sucesores = new String[3];
                sucesores[0] = " " + estadoActual.charAt(0) + estadoActual.substring(2);
                sucesores[1] = estadoActual.substring(0, 1) + estadoActual.charAt(2) + " " + estadoActual.substring(3);
                sucesores[2] = estadoActual.substring(0, 1) + estadoActual.charAt(4) + estadoActual.substring(2, 4) + " " + estadoActual.substring(5);
            break;
            case 2:
                sucesores = new String[2];
                sucesores[0] = estadoActual.substring(0, 1) + " " + estadoActual.charAt(1) + estadoActual.substring(3);
                sucesores[1] = estadoActual.substring(0, 2) + estadoActual.charAt(5) + estadoActual.substring(3, 5) + " " + estadoActual.substring(6);
            break;
            case 3:
                sucesores = new String[3];
                sucesores[0] = " " + estadoActual.substring(1, 3) + estadoActual.charAt(0) + estadoActual.substring(4);
                sucesores[1] = estadoActual.substring(0, 3) + estadoActual.charAt(4) + " " + estadoActual.substring(5);
                sucesores[2] = estadoActual.substring(0, 3) + estadoActual.charAt(6) + estadoActual.substring(4, 6) + " " + estadoActual.substring(7);
            break;
            case 4:
                sucesores = new String[4];
                sucesores[0] = estadoActual.substring(0, 1) + " " + estadoActual.substring(2, 4) + estadoActual.charAt(1) + estadoActual.substring(5);
                sucesores[1] = estadoActual.substring(0, 4) + estadoActual.charAt(7) + estadoActual.substring(5, 7) + " " + estadoActual.substring(8);
                sucesores[2] = estadoActual.substring(0, 3) + " " + estadoActual.charAt(3) + estadoActual.substring(5);
                sucesores[3] = estadoActual.substring(0, 4) + estadoActual.charAt(5) + " " + estadoActual.substring(6);
            break;
            case 5:
                sucesores = new String[3];
                sucesores[0] = estadoActual.substring(0, 2) + " " + estadoActual.substring(3, 5) + estadoActual.charAt(2) + estadoActual.substring(6);
                sucesores[1] = estadoActual.substring(0, 4) + " " + estadoActual.charAt(4) + estadoActual.substring(6);
                sucesores[2] = estadoActual.substring(0, 5) + estadoActual.charAt(8) + estadoActual.substring(6, 8) + " ";
            break;
            case 6:
                sucesores = new String[2];
                sucesores[0] = estadoActual.substring(0, 3) + " " + estadoActual.substring(4, 6) + estadoActual.charAt(3) + estadoActual.substring(7);
                sucesores[1] = estadoActual.substring(0, 6) + estadoActual.charAt(7) + " " + estadoActual.charAt(8);
            break;
            case 7:
                sucesores = new String[3];
                sucesores[0] = estadoActual.substring(0, 4) + " " + estadoActual.substring(5, 7) + estadoActual.charAt(4) + estadoActual.substring(8);
                sucesores[1] = estadoActual.substring(0, 6) + " " + estadoActual.charAt(6) + estadoActual.substring(8);
                sucesores[2] = estadoActual.substring(0, 7) + estadoActual.charAt(8) + " ";
            break;
            case 8:
                sucesores = new String[2];
                sucesores[0] = estadoActual.substring(0, 5) + " " + estadoActual.substring(6, 8) + estadoActual.charAt(5);
                sucesores[1] = estadoActual.substring(0, 7) + " " + estadoActual.charAt(7);
            break;
        }
        return sucesores;
    }
}