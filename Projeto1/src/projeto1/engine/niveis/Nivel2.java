/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.niveis;

import projeto1.entidades.elementos.frutinhas.Morango;


/**
 *
 * @author Vitor Rinaldini
 */
public class Nivel2 extends Nivel{
    /**
     * Construtor do nível 2;
     */
    public Nivel2() {
        super(2, ".//mapas//map1.txt", 70, 100, 30, Morango.class);
    }
    
}
