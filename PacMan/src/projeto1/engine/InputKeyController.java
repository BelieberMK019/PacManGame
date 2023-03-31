/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Vitor Rinaldini
 */
public class InputKeyController implements EventHandler<KeyEvent>{
    
    public boolean w = false;
    public boolean a = false;
    public boolean s = false;
    public boolean d = false;
    
    public boolean arrowUp = false;
    public boolean arrowLeft = false;
    public boolean arrowRight = false;
    public boolean arrowDown = false;
    
    public boolean enter = false;
    public boolean esc = false;
    
    public boolean exibir_fps = false;
    public boolean pausado = false;
    public boolean descer = false;
    public boolean subir = false;
    public boolean esquerda = false;
    public boolean direita = false;
    public boolean go = false;
    
    public void reset() {
        pausado = false;
        descer = false;
        subir = false;
        esquerda = false;
        direita = false;
    }
    
    public boolean isPausado() {
        
        boolean result = pausado;
        if(pausado == true) {
            pausado = false;
        }
        return result;
    }
    
    public boolean isCima() {
        
        boolean result = subir;
        if(subir == true) {
            subir = false;
        }
        return result;
    }
    
    public boolean isBaixo() {
        
        boolean result = descer;
        if(descer == true) {
            descer = false;
        }
        return result;
    }
    
    public boolean isEsquerda() {
        
        boolean result = esquerda;
        if(esquerda == true) {
            esquerda = false;
        }
        return result;
    }
    
    public boolean isDireita() {
        
        boolean result = direita;
        if(direita == true) {
            direita = false;
        }
        return result;
    }
    
    public boolean isGo() {
        
        boolean result = go;
        if(go == true) {
            go = false;
        }
        return result;
    }
    
    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode code = event.getCode();
            boolean valor = true;
            if(code == KeyCode.W) w = valor;
            else if(code == KeyCode.A) a = valor;
            else if(code == KeyCode.S) s = valor;
            else if(code == KeyCode.D) d = valor;
            else if(code == KeyCode.UP) arrowUp = valor;
            else if(code == KeyCode.LEFT) arrowLeft = valor;
            else if(code == KeyCode.RIGHT) arrowRight = valor;
            else if(code == KeyCode.DOWN) arrowDown = valor;
            else if(code == KeyCode.ESCAPE) esc = valor;
            else if(code == KeyCode.ENTER) enter = valor;
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyCode code = event.getCode();
            boolean valor = false;
            if(code == KeyCode.W) {
                subir = (!subir);
                w = valor;
            }
            else if(code == KeyCode.A) {
                esquerda = (!esquerda);
                a = valor;
            }
            else if(code == KeyCode.S) {
                descer = (!descer);
                s = valor;
            }
            else if(code == KeyCode.D) {
                direita = (!direita);
                d = valor;
            }
            else if(code == KeyCode.UP) {
                subir = (!subir);
                arrowUp = valor;
            }
            else if(code == KeyCode.LEFT) {
                esquerda = (!esquerda);
                arrowLeft = valor;
            }
            else if(code == KeyCode.RIGHT) {
                direita = (!direita);
                arrowRight = valor;
            }
            else if(code == KeyCode.DOWN) {
                descer = (!descer);
                arrowDown = valor;
            }
            else if(code == KeyCode.ESCAPE) {
                esc = valor;
                pausado = (!pausado);
            }
            else if(code == KeyCode.ENTER) {
                go = (!go);
                enter = valor;
            }
            else if(code == KeyCode.F11) exibir_fps = (!exibir_fps);
        }
    }
    
    
}
