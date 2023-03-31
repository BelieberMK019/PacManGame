/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

/**
 *
 * @author Vitor Rinaldini
 */
public class Memoria {
    
    public static void pritarMemoria() {
        int dataSize = 1024 * 1024;

        Runtime runtime = Runtime.getRuntime();

        System.out.println("Memoria máxima: " + runtime.maxMemory() / dataSize + "MB");
        System.out.println("Memoria total: " + runtime.totalMemory() / dataSize + "MB");
        System.out.println("Memoria livre: " + runtime.freeMemory() / dataSize + "MB");
        System.out.println("Memoria usada: " + (runtime.totalMemory() - runtime.freeMemory()) / dataSize + "MB");

    }
}
