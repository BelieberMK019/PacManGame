/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades;

import projeto1.engine.Mapa;
import projeto1.engine.Tabuleiro;
import projeto1.engine.grafo.Node;
import projeto1.entidades.fantasmas.Fantasma;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Entidade extends Node{
    /**
     * Classe responsável pelas funções basicas de uma Entidade.
     * @author Vítor Rinaldini
     */
    private Tabuleiro tabuleiro;
    private boolean vivo;
    private boolean presa;
    
    private float velocidade;
    
    public static final float VELOCIDADE_NORMAL = 1.0f;
    public static final float VELOCIDADE_REDUZIDA = 0.5f;
    public static final float VELOCIDADE_ACELERADA = 1.5f;
    
    /**
     * Construtor
     * @param x
     * @param y
     * @param tab 
     */
    public Entidade(float x, float y, Tabuleiro tab) {
        super(x, y);
        this.tabuleiro = tab;
        vivo = true;
        velocidade = VELOCIDADE_NORMAL;
        presa = false;
    }

    /**
     * Get Valocidade.
     * @return 
     */
    public float getVelocidade() {
        return velocidade;
    }
    
    /**
     * Set Velocidade.
     * @param v 
     */
    public void setVelocidade(float v) {
        if(v == VELOCIDADE_NORMAL){
            setX(Math.round(getX()));
            setY(Math.round(getY()));
        }
        velocidade = v;
    }
    
    /**
     * Muda a velocidade para a menor.
     */
    public void setVelocidadeToReduzida() {
        velocidade = VELOCIDADE_REDUZIDA;
    }
    
    /**
     * Muda a Velocidade para a acelerada.
     */
    public void setVelocidadeToAcelerada() {
        velocidade = VELOCIDADE_ACELERADA;
    }
    
    /**
     * Muda a velocidade para normal.
     */
    public void setVelocidadeToNormal() {
        velocidade = VELOCIDADE_NORMAL;
    }
    
    public abstract char getCharImag();
    
    /**
     * coloca a váriavel dentro dos limites do mapa.
     * @param var
     * @param max
     * @return 
     */
    private float normalizarVar(float var, float max) {
        if(var < 0) return max-1f;
        else if(var > max-1f) return 0;
        else return var;
    }
    
    /**
     * Move uma entidade em X;
     * @param dx 
     */
    public void moverX(float dx) {
        Mapa mapa = tabuleiro.getMapa();
        float xNormalizado = normalizarVar(getX()+dx, mapa.getXMax());
        if(mapa.isCaminho((int) (xNormalizado), (int) getY()) || (this instanceof Fantasma && mapa.isPorteira((int) (xNormalizado), (int) getY()))) {
            setX(xNormalizado);
        }
    }

    /**
     * Move uma entidade em Y;
     * @param dy 
     */
    public void moverY(float dy) {
        Mapa mapa = tabuleiro.getMapa();
        float yNormalizado = normalizarVar(getY()+dy, mapa.getYMax());
        if(mapa.isCaminho(Math.round(getX()), Math.round(yNormalizado)) || (this instanceof Fantasma && mapa.isPorteira(Math.round(getX()), Math.round(yNormalizado)))) {
            setY(yNormalizado);
        }
    }
    
    /**
     * Confere se está vivo.
     * @return 
     */
    public boolean isVivo() {
        return vivo;
    }
    
    /**
     * Mata a entidade.
     */
    public void morrer() {
        vivo = false;
    }
    
    /**
     * Revive a entidade.
     */
    public void viver() {
        vivo = true;
    }

    /**
     * Retorna o tabuleiro
     * @return 
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    
    /**
     * Confere se está presa.
     * @return 
     */
    public boolean isPresa() {
        return presa;
    }
    
    /**
     * Set se a entidade está presa.
     * @param presa 
     */
    public void setPresa(boolean presa) {
        this.presa = presa;
    }
    
}
