package es.progcipfpbatoi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestorIO {

    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static int obtenerEntero(String mensaje) {
        do {
            System.out.print(mensaje);
            if (!scanner.hasNextInt()) {
                AnsiColor.errorOutput("¡Error! Debe introducir un entero");
                scanner.next();
            } else {
                return scanner.nextInt();
            }
        } while (true);
    }

    public static String obtenerString(String mensaje) {
        System.out.print(mensaje + " : ");
        return scanner.next();
    }

    public static String obtenerTexto(String mensaje) {
        System.out.print(mensaje + " : ");
        return scanner.nextLine().trim();
    }

    public static boolean confirmar(String mensaje) {
        System.out.println(mensaje + " [S/N]");
        return scanner.next().toUpperCase().charAt(0) == 'S';
    }

    public static void limpiar() {
        scanner.nextLine();
    }

    /**
     * Retorna una fecha válida introducida por el usuario y establecida a las 08:00 horas
     */
    public static LocalDateTime obtenerFecha(String mensaje) {
        do {
            try {
                String date = GestorIO.obtenerString(mensaje);
                return validateDate(date);
            } catch (InputMismatchException ex) {
                AnsiColor.errorOutput(ex.getMessage());
            }
        }while(true);
    }

    /**
     * valida un string recibido para que cumpla con el formato de fecha establecidos
     * si es válido se establece la fecha devuelva a las 08:00 horas
     * @param date
     * @return la fecha correcta establecida a las 08:00
     * @throws InputMismatchException en caso de que el formato de @date no sea correcto
     */
    private static LocalDateTime validateDate(String date) throws InputMismatchException{
        try {
            return DateConversion.toLocalDateTime(date, 8, 0);
        } catch (DateTimeParseException e) {
            throw new InputMismatchException("El formato introducido no es válido. Recuerde (dd/mm/yyyy)");
        }
    }
}
