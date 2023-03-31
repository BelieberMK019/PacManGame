/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos.frutinhas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import projeto1.engine.Tabuleiro;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Laranja extends Frutinha implements Animacao{
    /**
     * Classe resposável pela implementação da Cereja
     * @author Vitor Rinaldini
     * @param x
     * @param y 
     */
    public Laranja(float x, float y, Tabuleiro tab) {
        super(x, y, tab, 500f);
    }

    private float raio = 1f;
    
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
    private final float DFASE = 1f;
    
    @Override
    public void draw(GraphicsContext g) {
        float x = getRealX();
        float y = getRealY();
        
        g.setFill(Color.BLACK);
        g.setFill(Color.ORANGE);
        
        Affine rotate = new Affine();
        rotate.appendRotation(40+5*Math.sin(fase), x, y);
        g.setTransform(rotate);
        
        g.fillOval(x-raio, y-raio, 2*raio, 2*raio);
        g.strokeOval(x-raio, y-raio, 2*raio, 2*raio);
        float xFolha = x;
        float yFolha = y-7*raio/8;
        float raioFolha = raio/2;
        
        g.setFill(Color.LIGHTGREEN);
        g.fillOval(xFolha-raioFolha, yFolha-raioFolha/4, 2*raioFolha, raioFolha/2);
        g.strokeOval(xFolha-raioFolha, yFolha-raioFolha/4, 2*raioFolha, raioFolha/2);
        
         g.save();
        g.beginPath();
        g.moveTo(xFolha, yFolha-raio/8);
        g.arcTo(xFolha, yFolha-raio, xFolha+raio/2, yFolha-raio/2, raio/3);
        
        double back = g.getLineWidth();
        g.setStroke(Color.BLACK);
        g.setLineWidth(back+5f);
        g.stroke();
        
        g.setStroke(Color.LIGHTGREEN);
        g.setLineWidth(5f);
        g.stroke();
        g.restore();
    }

    @Override
    public void refresh(float raio) {
        this.raio = 0.5f*raio;
    }
    
}
