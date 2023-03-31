/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import projeto1.engine.Mapa;
import projeto1.engine.uteis.Ponto2f;

/**
 *
 * @author Vitor Rinaldini
 */
public class TabuleiroMontado extends Ponto2f implements ElementosMapa{

    private TexturasGraficas textura;
    private float raio = 1f;
    private int altura;
    private Mapa mapa;
    
    private Color corFundo;
    private WritableImage imagem;
    
    public TabuleiroMontado(TexturasGraficas textura, float x, float y, int altura, Mapa mapa, Color corFundo) {
        super(x, y);
        this.textura = textura;
        this.mapa = mapa;
        this.corFundo = corFundo;
        setAltura(altura);
    }
    
    public void setAltura(int altura) {
        this.altura = altura;
        mapa.refresh(altura);
        int xMax = mapa.getXMax();
        raio = mapa.getRaio();
        criarMapa();  
    }
    
    public void criarMapa() { 
        ArrayList<ElementosMapa> elementos = mapa.criarMapa(textura);
        
        float raio = mapa.getRaio();
        for(ElementosMapa e: elementos) {
            e.refresh(raio);
            e.setRealX(raio+e.getRealX()*2*raio);
            e.setRealY(raio+e.getRealY()*2*raio);
        }
        Canvas canvas = new Canvas(mapa.getWidth(), mapa.getHeight());
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(corFundo);
        g.fillRect(0, 0, mapa.getWidth(), mapa.getHeight());
        for(ElementosMapa e: elementos) {
            g.save();
            e.draw(g);
            g.restore();
        }
        
        imagem = canvas.snapshot(null, null);
    }
    
    public WritableImage getImage() {
        return imagem;
    }
    
    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(imagem, getX(), getY());
    }

    @Override
    public void refresh(float raio) {
        this.raio = raio;
    }

    @Override
    public void setRealX(float x) {
        setX(x);
    }

    @Override
    public void setRealY(float y) {
        setY(y);
    }

    @Override
    public float getRealX() {
        return getX();
    }

    @Override
    public float getRealY() {
        return getY();
    }
    
}
