/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.niveis;

import projeto1.entidades.elementos.frutinhas.Frutinha;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Nivel {
    /**
     * Classe responsável por armazenar todas as informações referente ao nível daquela fase.
     * @author Vitor Rinaldini
     */
    private final int nivel;
    private final String FILE_NAME;
    
    private boolean minDotsSpawnado;
    private final int MIN_DOTS_TO_SPAWN_FRUTINHA;
    private final int DELTA_DOTS_TO_SPAWN_FRUTINHA;
    
    private final float TIME_WITH_PODER;
    
    private final float FATOR_VELOCIDADE_BLINKY;
    
    private final float TEMPO_BASE_FATASMA_SPAWN;
    
    private Class<? extends Frutinha> fruta;
    
    /**
     * Construtor do nível.
     * @param nivel
     * @param FILE_NAME
     * @param MIN_DOTS_TO_SPAWN_FRUTINHA
     * @param DELTA_DOTS_TO_SPAWN_FRUTINHA
     * @param TIME_WITH_PODER
     * @param fruta 
     */
    public Nivel(int nivel, String FILE_NAME, int MIN_DOTS_TO_SPAWN_FRUTINHA, int DELTA_DOTS_TO_SPAWN_FRUTINHA, float TIME_WITH_PODER, float FATOR_VELOCIDADE_BLINKY, float TEMPO_BASE_FANTASMA_SPAWN, Class<? extends Frutinha> fruta) {
        this.nivel = nivel;
        this.FILE_NAME = FILE_NAME;
        this.MIN_DOTS_TO_SPAWN_FRUTINHA = MIN_DOTS_TO_SPAWN_FRUTINHA;
        this.DELTA_DOTS_TO_SPAWN_FRUTINHA = DELTA_DOTS_TO_SPAWN_FRUTINHA;
        this.TIME_WITH_PODER = TIME_WITH_PODER;
        this.fruta = fruta;
        this.FATOR_VELOCIDADE_BLINKY = FATOR_VELOCIDADE_BLINKY;
        this.TEMPO_BASE_FATASMA_SPAWN = TEMPO_BASE_FANTASMA_SPAWN;
        minDotsSpawnado = false;
    }
    
    public static Nivel createNivel(int nivel) {
        if(nivel <= 0) return null;
        
        if(nivel == 1) {
            return new Nivel1();
        } else if(nivel == 2) {
            return new Nivel2();
        } else if(nivel == 3) {
            return new Nivel3();
        } else {
            return new NivelN(nivel);
        }
    }
    
    /**
     * Get File name do nivel
     * @return 
     */
    public String getFILE_NAME() {
        return FILE_NAME;
    }
    
     public float getTEMPO_BASE_FATASMA_SPAWN() {
        return TEMPO_BASE_FATASMA_SPAWN;
    }
    
    /**
     * Get o Minimo de Dots pegos para spawnar uma fruta. 
     * @param minDotsSpawnado 
     */
    public void setMinDotsSpawnado(boolean minDotsSpawnado) {
        this.minDotsSpawnado = minDotsSpawnado;
    }

    /**
     * confere se o minimo de dosts ja foi pego.
     * @return 
     */
    public boolean isMinDotsSpawnado() {
        return minDotsSpawnado;
    }

    /**
     * retorna o min de dots pegos para spawnar uma fruta.
     * @return 
     */
    public int getMIN_DOTS_TO_SPAWN_FRUTINHA() {
        return MIN_DOTS_TO_SPAWN_FRUTINHA;
    }

    /**
     * Get delta de dots pegos para spawnar outra fruta.
     * @return 
     */
    public int getDELTA_DOTS_TO_SPAWN_FRUTINHA() {
        return DELTA_DOTS_TO_SPAWN_FRUTINHA;
    }

    /**
     * Get time com poder naquele nível.
     * @return 
     */
    public float getTIME_WITH_PODER() {
        return TIME_WITH_PODER;
    }

    /**
     * Set o tipo de fruta daquele nível.
     * @param fruta 
     */
    public void setFruta(Class<? extends Frutinha> fruta) {
        this.fruta = fruta;
    }

    /**
     * Retorna o tipo de fruta daquele nível.
     * @return 
     */
    public Class<? extends Frutinha> getFrutaTipo() {
        return fruta;
    }

    /**
     * retorna o valor do nivel.
     * @return 
     */
    public int getNivel() {
        return nivel;
    }
    
    public float getFATOR_VELOCIDADE_BLINKY() {
        return FATOR_VELOCIDADE_BLINKY;
    }
    
}
