/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import projeto1.engine.Tabuleiro;

/**
 *
 * @author Vitor Rinaldini
 */
public class PacDots extends Ponto{
    /**
     * Classe responsável pelo objeto PacDots.
     * @param x
     * @param y 
     */
    public PacDots(float x, float y, Tabuleiro tab) {
        super(x, y, tab);
    }
    
    @Override
    public void update(double deltaTime) {
        fase += deltaTime*DFASE;
        if(fase > 2*Math.PI) fase -= 2*Math.PI;
    }
    
    private float fase = 0.0f;
    private final float DFASE = 5f;
    private float raio = 1f;
    @Override
    public void draw(GraphicsContext g) {
        float x = getRealX();
        float y = getRealY();
        
        RadialGradient grad = new RadialGradient(0, .1, x, y, 
                raio, false, 
                CycleMethod.NO_CYCLE, 
                new Stop(0, Color.LIGHTYELLOW), 
                new Stop(0.6+0.05*Math.sin(fase), Color.YELLOW),
                new Stop(1, Color.ORANGE));
        g.setFill(grad);
        g.fillOval(x-raio, y-raio, 2*raio, 2*raio);
        
    }

    @Override
    public void refresh(float raio) {
        this.raio = 0.2f*raio;
    }
    
}
