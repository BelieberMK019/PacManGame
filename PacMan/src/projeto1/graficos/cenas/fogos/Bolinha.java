/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.fogos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projeto1.engine.uteis.Ponto2f;
import projeto1.engine.uteis.Vetor2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Bolinha extends Ponto2f implements Animacao{
    
    
    private Vetor2f velocidadeInicial;
    private float gravidade = 100;
    private float VELOCIDADE = 100;
    private Color cor;
    
    private boolean finish = false;
    
    private float time;
    private float xTime;
    private float yTime;
    
    private float raio = 1f;
    
    private float yLimite;
    public Bolinha(float x, float y, Vetor2f dir, Color cor, float yLimite) {
        super(x, y);
        this.cor = cor;
    
        xTime = x;
        yTime = y;
        time = 0;       
        this.yLimite = yLimite;
        this.velocidadeInicial = (dir.normalizado()).produto(VELOCIDADE);
    }

    @Override
    public boolean isFinish() {
        return finish;
    }

    @Override
    public void update(double deltaTime) {
        if(yTime > yLimite) {
            finish = true;
            return;
        }
        
        time += (float) deltaTime;

        Vetor2f posInicial = new Vetor2f(getX(), getY());

        Vetor2f pos = posInicial.soma(velocidadeInicial.produto((float) time));
        Vetor2f gravidade = new Vetor2f(0, +this.gravidade);
        pos = pos.soma(gravidade.produto((float) (time * time / 2f)));

        xTime = pos.getX();
        yTime = pos.getY();
    }

    @Override
    public void draw(GraphicsContext g) {
        if(yTime > yLimite) return;
        g.setStroke(Color.BLACK);
        g.setFill(cor);

        float x = xTime;
        float y = yTime;

        g.fillOval(x - raio, y - raio, 2 * raio, 2 * raio);
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
