/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos.frutinhas;

import projeto1.entidades.elementos.Ponto;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Frutinha extends Ponto{
    /**
     * Classe responsável pela implementação da frutinha.
     * @author Vitor Rinaldini.
     * @param x
     * @param y
     * @param img
     * @param pontuacao 
     */
    public Frutinha(float x, float y, char img, float pontuacao) {
        super(x, y, img);
        setVALOR(pontuacao);
    }
    
}
