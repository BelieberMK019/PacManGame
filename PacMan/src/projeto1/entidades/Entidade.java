/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades;

import projeto1.engine.Mapa;
import projeto1.engine.Tabuleiro;
import projeto1.engine.grafo.Node;
import projeto1.engine.uteis.Ponto2f;
import projeto1.entidades.fantasmas.Fantasma;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Entidade extends Node implements Animacao{
    /**
     * Classe responsável pelas funções basicas de uma Entidade.
     * @author Vítor Rinaldini
     */
    private Tabuleiro tabuleiro;
    private boolean vivo;
    private boolean presa;
    
    private float velocidade;
    
    public static final float VELOCIDADE_NORMAL = 100.0f;
    public static final float VELOCIDADE_REDUZIDA = 50f;
    public static final float VELOCIDADE_ACELERADA = 150f;
    
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
        else if(var >= max) return 0;
        else return var;
    }
    
    /**
     * Move uma entidade em X;
     * @param dx 
     */
    public void moverX(float dx) {
        Mapa mapa = tabuleiro.getMapa();
        float c = 0;
        float raio = mapa.getRaio()/3.0f; 
        if(dx > 0) c = raio;
        else if(dx < 0) c = -raio;
        
        Ponto2f imaginary = mapa.getImaginaryPosition(getX()+c, getY(), tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        Ponto2f atual = mapa.getImaginaryPosition(getX(), getY(), tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        float xNormalizado = normalizarVar((imaginary.getY()), mapa.getYMax());
        Ponto2f correcao = mapa.getRealPosition((int) imaginary.getX(),(int) xNormalizado,tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        if(mapa.isCaminho((int) imaginary.getX(), (int) (xNormalizado)) || (this instanceof Fantasma && mapa.isPorteira((int) imaginary.getX(), (int) (xNormalizado)))) {
            if(!atual.isIgual(imaginary)) setY(correcao.getY());
            if(imaginary.getY() >= 0 && imaginary.getY() < mapa.getYMax()) setX(getX()+dx);
            else setX(correcao.getX());
        }
    }

    /**
     * Move uma entidade em Y;
     * @param dy 
     */
    public void moverY(float dy) {
        Mapa mapa = tabuleiro.getMapa();
        
        float c = 0;
        if(dy > 0) c = mapa.getRaio();
        else if(dy < 0) c = -mapa.getRaio();
        
        Ponto2f imaginary = mapa.getImaginaryPosition(getX(), getY()+c, tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        Ponto2f atual = mapa.getImaginaryPosition(getX(), getY(), tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        float yNormalizado = normalizarVar((imaginary.getX()), mapa.getXMax());
        Ponto2f correcao = mapa.getRealPosition((int) yNormalizado,(int) imaginary.getY(),tabuleiro.getTabuleiroMontado().getRealX(), tabuleiro.getTabuleiroMontado().getRealY());
        if(mapa.isCaminho((int) (yNormalizado), (int) imaginary.getY()) || (this instanceof Fantasma && mapa.isPorteira((int) (yNormalizado), (int) imaginary.getY()))) {
            if(!atual.isIgual(imaginary)) setX(correcao.getX());
            if(imaginary.getX() >= 0 && imaginary.getX() < mapa.getXMax()) setY(getY()+dy);
            else setY(correcao.getY());
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
    
    @Override
    public float getRealX() {
        return getX();
    }

    @Override
    public float getRealY() {
        return getY();
    }

    @Override
    public void setRealX(float x) {
        setX(x);
    }

    @Override
    public void setRealY(float y) {
        setY(y);
    }
}
