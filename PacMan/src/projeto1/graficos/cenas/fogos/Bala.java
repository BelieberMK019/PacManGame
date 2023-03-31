/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.fogos;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projeto1.engine.uteis.Ponto2f;
import projeto1.engine.uteis.Vetor2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Bala extends Ponto2f implements Animacao{

    private float raio = 1f;
    
    private Vetor2f velocidadeInicial;
    private final float gravidade = 300f;
    private final float VELOCIDADE = 500f;
    private final float TIME_ESTOURAR = 2;
 
    float time = 0;
    float nTIME_ESTOURAR = TIME_ESTOURAR;

    float xTime;
    float yTime;
    
    public boolean estourar = false;
    
    public Bala(float x, float y, Vetor2f direcao) {
        super(x, y);
        reset(x, y, direcao);
    }
    
    public void reset(float x, float y, Vetor2f direcao) {
        setX(x);
        setY(y);
        
        time = 0;
        estourar = false;
        
        xTime = x;
        yTime = y;
        this.velocidadeInicial = (direcao.normalizado()).produto(VELOCIDADE);
    
        Random rand = new Random();
        nTIME_ESTOURAR = TIME_ESTOURAR-(rand.nextInt(1000)/1000f);
    }
    
    @Override
    public boolean isFinish() {
        return false;
    }
    
    public boolean isEstourar() {
        return estourar;
    }

    @Override
    public void update(double deltaTime) {
        if(!estourar) {
            time += (float) deltaTime;

            Vetor2f posInicial = new Vetor2f(getX(), getY());

            Vetor2f pos = posInicial.soma(velocidadeInicial.produto((float) time));
            Vetor2f gravidade = new Vetor2f(0, +this.gravidade);
            pos = pos.soma(gravidade.produto((float) (time * time / 2f)));

            xTime = pos.getX();
            yTime = pos.getY();
            
            if(time >= nTIME_ESTOURAR) estourar = true;
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        if(!estourar) {
            g.setStroke(Color.BLACK);
            g.setFill(Color.WHITE);

            float x = xTime;
            float y = yTime;

            g.fillOval(x - raio, y - raio, 2 * raio, 2 * raio);
        }
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
    
    public float getXEsperial() {
        return xTime;
    }
 
    public float getYEsperial() {
        return yTime;
    }
    
}
