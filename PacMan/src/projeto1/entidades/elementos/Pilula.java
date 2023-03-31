/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import projeto1.engine.Tabuleiro;

/**
 *
 * @author Vitor Rinaldini
 */
public class Pilula extends Ponto {
    /**
     * Classe responsável pela implementação da Pílula.
     * @author Vitor Rinaldini
     */
    public Pilula(float x, float y, Tabuleiro tab) {
        super(x, y, tab);
        setVALOR(40.0f);
    }
    
    @Override
    public void update(double deltaTime) {

        fase += deltaTime*DFASE;
        if(fase > 2*Math.PI) fase -= 2*Math.PI;
    }

    float fase = 0.0f;
    final float DFASE = 1f;
    private float raio = 1f;
    
    @Override
    public void draw(GraphicsContext g) {
        
        float x = getRealX();
        float y = getRealY();
        
        float dist = raio;
        
        Affine rotate = new Affine();
        rotate.appendRotation(-45+5*Math.sin(fase), x, y);
        g.setTransform(rotate);
        g.setStroke(Color.BLACK);
        
        g.beginPath();
        g.moveTo(x, y-raio);
        g.lineTo(x-dist, y-raio);
        g.arc(x-dist, y, raio, raio, 90, 180);
        g.lineTo(x, y+raio);
        g.closePath();
        g.setFill(Color.RED);
        g.fill();
        g.stroke();
        
        g.beginPath();
        g.moveTo(x, y-raio);
        g.lineTo(x+dist, y-raio);
        g.arc(x+dist, y, raio, raio, 90, -180);
        g.lineTo(x, y+raio);
        g.closePath();
        g.setFill(Color.LIGHTCYAN);
        g.fill();
        g.stroke();
        
    }

    @Override
    public void refresh(float raio) {
        this.raio = 0.4f*raio;
    }
    
}
