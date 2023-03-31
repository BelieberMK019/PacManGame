/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos;

import javafx.geometry.Rectangle2D;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Ponto2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Ponto extends Ponto2f implements Animacao{
    /**
     * Classe responsável pela implementação dos objetos que valerão pontos ou terão efeitos sobre o jogador.
     * @author Vitor Rinaldini
     */
    
    private float VALOR = 10.0f;
    private Tabuleiro tab;
    private boolean finish = false;
    /**
     * Construtor.
     * @param x
     * @param y
     * @param imag 
     */
    public Ponto(float x, float y, Tabuleiro tab) {
        super(x, y);
        this.tab = tab;
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
    
    @Override
    public float getRealX() {
        return tab.getMapa().getRealPosition((int)getX(),(int) getY(), tab.getTabuleiroMontado().getX(), tab.getTabuleiroMontado().getY()).getX();
    }

    @Override
    public float getRealY() {
        return tab.getMapa().getRealPosition((int)getX(),(int) getY(), tab.getTabuleiroMontado().getX(), tab.getTabuleiroMontado().getY()).getY();
    }

    @Override
    public void setRealX(float x) {
        setX(x);
    }

    @Override
    public void setRealY(float y) {
        setY(y);
    }
    
    public void finalizar() {
        finish = true;
    }

    @Override
    public boolean isFinish() {
        return finish;
    }

    @Override
    public Rectangle2D getBox(float raio) {
        Rectangle2D rec = new Rectangle2D(getRealX()-0.3*raio, getRealY()-0.3*raio, 0.6*raio, 0.6*raio);
        return rec;
    }
    
}
