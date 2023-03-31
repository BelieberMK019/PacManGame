/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import projeto1.engine.InputKeyController;
import projeto1.graficos.Pincel;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Cena {
    
    private InputKeyController teclado;
    
    private boolean started = false;
    
    public boolean isStarted() {
        return started;
    }
    
    public void setStarted(boolean started) {
        this.started = started;
    }
    
    public Cena(InputKeyController teclado) {
        this.teclado = teclado;
    }
    
    public InputKeyController getTeclado() {
        return teclado;
    }
    
    public abstract void render(Pincel pincel);
    public void start() {
        started = true;
    }
    public void stop() {
        started = false;
    }
    public abstract void pause();
    public abstract void update(double deltaTime);
    public abstract void prepare();
    public abstract void finalize();
    
    
}
