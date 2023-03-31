/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos.frutinhas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import projeto1.engine.Tabuleiro;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Morango extends Frutinha implements Animacao{
    
    private ImagePattern imagem;
    
    /**
     * Classe responsavel pela implementação do Morango
     * @author Vitor Rinaldini
     * @param x
     * @param y 
     */
    public Morango(float x, float y, Tabuleiro tab) {
        super(x, y, tab, 300f);
        imagem = createMaterial();
    }
    
     public ImagePattern createMaterial() {
        
        WritableImage imagem = new WritableImage(400, 400);
        PixelWriter px = imagem.getPixelWriter();
        
        Color c = Color.RED;
        for(int i = 0; i < 400; i++) {
            for(int j = 0; j < 400; j++) {
                px.setColor(i, j, c);
            }
        }
        
        int limite = (int) 2;
        
        for (int j = 0; j < 400; j += 10) {
            for (int k = 0; k < 500; k += 15) {
                for(int dx = -limite/2; dx < limite/2; dx++) {
                    for(int dy = 0; dy < 2*limite; dy++) {
                        int xAux = j+dx;
                        int yAux = k+dy;
                        if (xAux < 400 && xAux >= 0 && yAux < 400 && yAux >= 0) {
                            px.setColor(xAux, yAux, Color.WHITE);
                        }
                    }
                }
            }
        }
        
        ImagePattern material = new ImagePattern(imagem);
        return material;
    }
    
    @Override
    public void update(double deltaTime) {      
        fase += deltaTime*DFASE;
        if(fase > 2*Math.PI) fase -= 2*Math.PI;
        
    }
    
    private float fase = 0.0f;
    private final float DFASE = 1f;
    private float raio = 1f;
    
    @Override
    public void draw(GraphicsContext g) {
        float x = getRealX();
        float y = getRealY();
        
        /*Affine rotate = new Affine();
        rotate.appendRotation(5*Math.sin(fase), x, y);
        g.setTransform(rotate);*/
        
        float raio = this.raio;
        double angle = (30*Math.PI/180.0f);
        double compleAngle = (60*Math.PI/180.0f);
        double dist = raio/Math.cos(angle);
        
        float xCenter1 = (float) (x-raio), xCenter2 = (float) (x+raio), xCenter3 = x;
        float yCenter1 = (float) (y-dist*Math.cos(compleAngle)), yCenter2 = (float) (y-dist*Math.cos(compleAngle)), yCenter3 = (float) (y+dist);
         
        g.beginPath();
        g.moveTo(xCenter1, yCenter1-raio);
        g.arc(xCenter1, yCenter1, raio, raio, 90,30+90);
        g.arc(xCenter3, yCenter3, raio, raio, 210, 120);
        g.arc(xCenter2, yCenter2, raio, raio, -30,90+30);
        g.closePath();
        
        g.setFill(imagem);
        g.setStroke(Color.BLACK);
        g.fill();
        g.stroke();
        
        float raioFlor = raio;
        g.setFill(Color.LIGHTGREEN);
        g.setStroke(Color.BLACK);
        g.fillOval(x-raioFlor, (y-dist*Math.cos(compleAngle)-raio-raioFlor/3), 2*raioFlor,2*raioFlor/3);
        g.strokeOval(x-raioFlor, (y-dist*Math.cos(compleAngle)-raio-raioFlor/3), 2*raioFlor,2*raioFlor/3);
        
        g.save();
        g.beginPath();
        g.moveTo(x, (y-dist*Math.cos(compleAngle)-raio));
        g.arcTo(x, (y-dist*Math.cos(compleAngle)-raio)-raio, x+raio/2, (y-dist*Math.cos(compleAngle)-raio)-raio/2, raio/3);
        
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
        this.raio = 0.25f*raio;
    }
    
    
}
