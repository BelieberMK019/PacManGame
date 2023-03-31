/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos;

import projeto1.engine.uteis.Ponto2f;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Ponto extends Ponto2f{
    /**
     * Classe responsável pela implementação dos objetos que valerão pontos ou terão efeitos sobre o jogador.
     * @author Vitor Rinaldini
     */
    private char charImg;
    
    private float VALOR = 10.0f;
    /**
     * Construtor.
     * @param x
     * @param y
     * @param imag 
     */
    public Ponto(float x, float y, char imag) {
        super(x, y);
        this.charImg = imag;
    }
    
    /**
     * retorna a imagem do ponto para imprimir no terminal.
     * @return 
     */
    public char getImg() {
        return charImg;
    }

    /**
     * Muda a imagem do ponto.
     * @param charImg 
     */
    public void setCharImg(char charImg) {
        this.charImg = charImg;
    }
    
    /**
     * retorna o valor daquele Ponto.
     * @return 
     */
    public float getValor() {
        return VALOR;
    }

    /**
     * altera o valor daquele ponto.
     * @param VALOR 
     */
    public void setVALOR(float VALOR) {
        this.VALOR = VALOR;
    }
    
    
}
