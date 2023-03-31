/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.niveis;

import projeto1.entidades.elementos.frutinhas.Laranja;

/**
 *
 * @author Vitor Rinaldini
 */
public class Nivel3 extends Nivel{
    /**
     * Construtor do nível 3;
     */
    public Nivel3() {
        super(3, ".//mapas//map1.txt", 70, 100, 7, 0.1f, 2, Laranja.class);
    }
    
}
