/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author adrian
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Horario> horario = new ArrayList<>();

        leerFichero(horario);

        // Ordenar por orden alfabético de profesor
        Collections.sort(horario, new Comparator<Horario>() {
            public int compare(Horario obj1, Horario obj2) {
                return obj1.getProfesor().compareTo(obj2.getProfesor());
            }
        });

        System.out.println("Datos del horario leidos: ");
        for (Horario temp : horario) {
            System.out.println(temp.toString());
        }

        System.out.println("Elija su opción:"
                + "\n1) Consultar horarios por profesor/a"
                + "\n2) Consultar horarios por grupos");
        int opcion = teclado.nextInt();

        switch (opcion) {
            case 1:
                for (Horario temp : horario) {
                    System.out.println(temp.getProfesor());
                }
                System.out.println("Elija un profesor de la lista:");
                String opcionProfesor = teclado.next();

                String ficheroProfesor = opcionProfesor + ".csv";
                try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(ficheroProfesor))) {
                    for (int i = 0; i < horario.size(); i++) {
                        if (horario.contains(opcionProfesor)) {
                            flujo.write(horario.get(i).toString());
                            flujo.newLine();
                        }
                    }

//          Metodo fluh() guarda cambios en disco 
                    flujo.flush();

                    System.out.println("Fichero " + ficheroProfesor + " creado correctamente.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                Collections.sort(horario, new Comparator<Horario>() {
                    public int compare(Horario obj1, Horario obj2) {
                        return obj1.getClase().compareTo(obj2.getClase());
                    }
                });
                for (Horario temp : horario) {
                    System.out.println(temp.getClase());
                }
                System.out.println("Elija una clase de la lista:");
                String opcionClase = teclado.next();
                
                String ficheroClase = opcionClase + ".csv";
                try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(ficheroClase))) {
                    for (int i = 0; i < horario.size(); i++) {
                        if (horario.contains(opcionClase)) {
                            flujo.write(horario.get(i).toString());
                            flujo.newLine();
                        }
                    }

//          Metodo fluh() guarda cambios en disco 
                    flujo.flush();

                    System.out.println("Fichero " + ficheroClase + " creado correctamente.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
        }

    }

    private static String refactor(String token) {

        if (token.equals("")) {
            return "sin datos";
        } else {
            String cadenaFormateada = token.substring(1, token.length() - 1);
            return cadenaFormateada;
        }
    }

    private static void leerFichero(ArrayList<Horario> horario) {

        //nombre del archivo que vamos a leer
        String idFichero = "hor_curso_1920_final.csv";

        //variable donde almacenaremos la linea completa
        String linea;

        //Array donde almacenaremos las divisiones
        String[] tokens;

        try ( Scanner fichero = new Scanner(new File(idFichero), "ISO-8859-1")) {

//          eliminamos la primera linea, ya que es la que nos da la info de que
//          campos tenemos que almacenar
            fichero.nextLine();

            while (fichero.hasNextLine()) {

//              Almacenamos la informacion de la linea en un string
                linea = fichero.nextLine();

//              dividimos la linea con le caracter que divide cada uno de los
//              diferentes campos y los metemos en un array
                tokens = linea.split(";");

//              Instanciamos un objeto de tipo Trabajador
                Horario tmp = new Horario();

//              Ahora lo que hacemos es usar los setters de la clase para crear
//              cada uno de los empleados
                tmp.setClase(refactor(tokens[1]));
                tmp.setProfesor(refactor(tokens[2]));
                tmp.setAsignatura(refactor(tokens[3]));
                tmp.setAula(refactor(tokens[4]));
                tmp.setDia(Integer.parseInt(tokens[5]));
                tmp.setHora(Integer.parseInt(tokens[6]));

                horario.add(tmp);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*private static void escribirEnFichero(ArrayList<Horario> lista) {

        String idFichero = "Horario.getProfesor.csv";

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero))) {

//          Escribimos en la primera linea el titulo de cada una de las celdas
//          y una nueva linea (cada vez quie escribamos)
            flujo.write("Clase,Asignatura,Aula,Dia,Hora");

            flujo.newLine();

            for (int i = 0; i < lista.size(); i++) {

                if (isMayorDe20(lista.get(i))) {
                    flujo.write(lista.get(i).toString());
                    flujo.newLine();
                }
            }

//          Metodo fluh() guarda cambios en disco 
            flujo.flush();

            System.out.println("Fichero " + idFichero + " creado correctamente.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }*/
}
