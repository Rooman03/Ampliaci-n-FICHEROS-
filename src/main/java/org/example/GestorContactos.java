package org.example;

import java.io.*;
import java.util.*;

public class GestorContactos {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Contacto> contactos = new ArrayList<>();
    private static final String ARCHIVO = "contactos.txt";

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            switch (opcion) {
                case 1: añadirContacto(); break;
                case 2: listarContactos(); break;
                case 3: buscarContacto(); break;
                case 4: eliminarContacto(); break;
                case 5: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Gestor de Contactos ---");
        System.out.println("1. Añadir contacto");
        System.out.println("2. Listar contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void añadirContacto() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        Contacto contacto = new Contacto(nombre, telefono, email);
        contactos.add(contacto);
        guardarContactos();
    }

    private static void listarContactos() {
        cargarContactos();
        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }
    }

    private static void buscarContacto() {
        System.out.print("Ingrese nombre a buscar: ");
        String nombre = scanner.nextLine();
        cargarContactos();
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(contacto);
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }

    private static void eliminarContacto() {
        System.out.print("Ingrese nombre a eliminar: ");
        String nombre = scanner.nextLine();
        cargarContactos();
        Iterator<Contacto> iterator = contactos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getNombre().equalsIgnoreCase(nombre)) {
                iterator.remove();
                guardarContactos();
                System.out.println("Contacto eliminado.");
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }

    private static void cargarContactos() {
        contactos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                contactos.add(new Contacto(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar contactos.");
        }
    }

    private static void guardarContactos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Contacto contacto : contactos) {
                writer.write(contacto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar contactos.");
        }
    }
}
