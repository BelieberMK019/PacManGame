/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos.frutinhas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projeto1.engine.Tabuleiro;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Cereja extends Frutinha implements Animacao{
    /**
     * Classe responsável pela implementação da Cereja
     * @author Vitor Rinaldini
     * @param x
     * @param y 
     */
    public Cereja(float x, float y, Tabuleiro tab) {
        super(x, y, tab, 100f);
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        fase += deltaTime*DFASE;
        if(fase > 2*Math.PI) fase -= 2*Math.PI;
    }
    
    private float fase = 0.0f;
    private final float DFASE = 0.75f;
    private float raio = 1f;
    
    @Override
    public void draw(GraphicsContext g) {
        float x = getRealX();
        float y = getRealY();
        
        float amplitude = raio/20;
        g.setStroke(Color.BLACK);
        g.setFill(Color.RED);
        
        g.strokeOval(x-raio/4+amplitude*Math.sin(fase), y, raio/2, raio/2);
        g.fillOval(x-raio/4+amplitude*Math.sin(fase), y, raio/2, raio/2);
        
        double back = g.getLineWidth();
        g.setStroke(Color.WHITESMOKE);
        g.setLineWidth(raio/30);
        g.beginPath();
        g.arc(x+amplitude*Math.sin(fase), y+raio/4, raio/6, raio/6, 200, 30);
        g.stroke();
        g.setStroke(Color.BLACK);
        g.setLineWidth(back);
        
        g.strokeOval(x-amplitude*Math.sin(fase), y+raio/8, raio/2, raio/2);
        g.fillOval(x-amplitude*Math.sin(fase), y+raio/8, raio/2, raio/2);
        
        back = g.getLineWidth();
        g.setStroke(Color.WHITESMOKE);
        g.setLineWidth(raio/30);
        g.beginPath();
        g.arc(x+raio/4-amplitude*Math.sin(fase), y+raio/4+raio/8, raio/6, raio/6, 200, 30);
        g.stroke();
        g.setStroke(Color.BLACK);
        g.setLineWidth(back);
        
        
        float xLig = x+raio/2;
        float yLig = y-raio/5;
        
        g.beginPath();
        g.moveTo(x+raio/12+amplitude*Math.sin(fase), y);
        g.arcTo(x+raio/5, y-raio/7, xLig, yLig, raio/5);
        g.lineTo(xLig, yLig);
        g.lineTo(xLig+raio/10, yLig-raio/10);
        g.lineTo(xLig, yLig);
        
        
        g.arcTo(x+raio/5+raio/5, y-raio/15, x+raio/5+amplitude*Math.sin(fase), y+raio/12, raio/5);
        g.lineTo(x+raio/3-amplitude*Math.sin(fase), y+raio/7);
        
        
        double stroke = raio/10;
        back = g.getLineWidth();
        g.setStroke(Color.BLACK);
        g.setLineWidth(back+stroke);
        g.stroke();
        
        g.setStroke(Color.LIGHTGREEN);
        g.setLineWidth(stroke);
        g.stroke();
    }

    @Override
    public void refresh(float raio) {
        this.raio = 1.5f*raio;
    }
    
}
