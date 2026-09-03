package util;

public class Validaciones {

   public static boolean campoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esCorreoValido(String correo) {
        if (campoVacio(correo)) {
            return false;
        }
        String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return correo.matches(patron);
    }
}