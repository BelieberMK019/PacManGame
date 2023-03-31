/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Vitor Rinaldini
 */
public interface Animacao {
    public boolean isFinish();
    public void update(double deltaTime);
    public void draw(GraphicsContext g);
    public void refresh(float raio);
    
    public void setRealX(float x);
    public void setRealY(float y);
    public float getRealX();
    public float getRealY();
    
}
