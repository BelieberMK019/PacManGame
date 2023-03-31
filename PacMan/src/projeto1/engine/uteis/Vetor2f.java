/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.uteis;

/**
 *
 * @author Vitor Rinaldini
 */
public class Vetor2f {
    private float x, y;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    public Vetor2f() {
        x = 0f;
        y = 0f;
    }
    
    public Vetor2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vetor2f soma(Vetor2f v) {
        Vetor2f result = new Vetor2f();
        result.setX(x+v.x);
        result.setY(y+v.y);
        return result;
    }
    
    public float modulo() {
        return (float) Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
    }
    
    public Vetor2f normalizado() {
       Vetor2f result = new Vetor2f(x, y);
       float modulo = modulo();
       result.x /= modulo;
       result.y /= modulo;
       return result;
    }
    
    public Vetor2f produto(float constante) {
        Vetor2f result = new Vetor2f(x, y);
        result.x *= constante;
        result.y *= constante;
        return result;
    }
}
