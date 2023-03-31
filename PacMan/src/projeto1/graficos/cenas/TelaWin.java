/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Ponto2f;
import projeto1.engine.uteis.Vetor2f;
import projeto1.graficos.GameLoop;
import projeto1.graficos.cenas.fogos.Bala;
import projeto1.graficos.cenas.fogos.Bolinha;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class TelaWin extends Ponto2f implements Animacao{

    private float raio = 1f;
    
    ArrayList<Bala> balas;
    ArrayList<Bolinha> bolinhas;
    InputKeyController teclado;
    
    Tabuleiro tab;
    
    GameLoop game;
    public TelaWin(float xCenter, float yCenter, float raio, Tabuleiro tabuleiro, InputKeyController teclado) {
        super(xCenter, yCenter);
        this.teclado = teclado;
        this.raio = raio;
        balas = new ArrayList();
        bolinhas = new ArrayList();
        this.tab = tabuleiro;
        for(int i = 0; i < 4; i++) criarBala();
    }
    
    public void criarBala() {
        Random rand = new Random();
        float raio = 0.7f*this.raio;
        float x = getX()-raio+rand.nextInt(1000)/1000f*(2*raio);
        float y = getY()+this.raio;
        
        int correcao = 1;
        if(x > getX()) correcao *= -1;
        
        Bala b = new Bala(x, y, new Vetor2f(0.25f*correcao, -1f));
        b.refresh(10f);
        balas.add(b);
    }
    
    public void resetarBala(Bala b) {
        Random rand = new Random();
        float raio = 0.7f*this.raio;
        float x = getX()-raio+rand.nextInt(1000)/1000f*(2*raio);
        float y = getY()+this.raio;
        
        int correcao = 1;
        if(x > getX()) correcao *= -1;
        
        b.reset(x, y, new Vetor2f(0.25f*correcao, -1f));
    }
    
    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        if(teclado.isGo()) {
            tab.getLoop().mudarToLoad();
            tab.getLoop().createAndLoadFase(tab.getPlayer(), tab.getNivel().getNivel()+1, tab.getTextura());
            return;
        }
        
        for(Bala b: balas) {
           if(!b.isEstourar()) b.update(deltaTime);
           else {
               for(int i = 0; i < 360; i += 30) {
                   float angle = (float) (i*Math.PI/180f);
                   Vetor2f dir = new Vetor2f((float)Math.cos(angle), (float)-Math.sin(angle));
                   Random rand = new Random();
                   Color cor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
                   Bolinha bo = new Bolinha(b.getXEsperial(), b.getYEsperial(), dir, cor, 3*raio);
                   bo.refresh(3f);
                   bolinhas.add(bo);
               }
               resetarBala(b);
               
           }
        }
        
        for(Bolinha e: bolinhas) {
            if(!isFinish()) e.update(deltaTime);
            else bolinhas.remove(e);
        }
    }
    
    public void renderMensagem(GraphicsContext g) {
        g.save();
        
        float xCenter = tab.getTabuleiroMontado().getX()+tab.getMapa().getWidth()/2f;
        float yCenter = tab.getTabuleiroMontado().getX()+tab.getMapa().getHeight()/2f;
        float raio = 15*tab.getMapa().getRaio();
        float raioBorda = tab.getMapa().getRaio();
        
        g.setFill(Color.BLACK);
        g.setGlobalAlpha(0.4);
        
        g.fillRoundRect(xCenter-raio-0.5*raio, yCenter-raio/2, 3*raio, raio, raioBorda, raioBorda);
        
        g.setGlobalAlpha(1.0);
        g.setLineWidth(4f);
        g.setStroke(Color.BLACK);
        
        g.strokeRoundRect(xCenter-raio-0.5f*raio, yCenter-raio/2, 3*raio, raio, raioBorda, raioBorda);
        
        g.setLineWidth(0.5f);
        g.setStroke(Color.WHITE);
        
        g.strokeRoundRect(xCenter-raio-0.5f*raio, yCenter-raio/2, 3*raio, raio, raioBorda, raioBorda);
        
        g.setLineWidth(1f);
        
        Text text = new Text(xCenter, yCenter, "Parabéns, você ganhou esse nível!");
        text.setFont(new Font("Verdana", raio/8));
        text.setX(xCenter-text.getLayoutBounds().getWidth()/2);
        text.setY(yCenter-text.getLayoutBounds().getHeight()/2);
        
        g.setFont(text.getFont());
        g.setStroke(Color.WHITE);
        g.setFill(Color.DARKGREEN);
        
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        float pontuacao = tab.getPlayer().getScoreAcumulado()+tab.getPlayer().getScore();
        text.setText("Sua pontuação total é de: "+ pontuacao);
        text.setX(xCenter-text.getLayoutBounds().getWidth()/2);
        text.setY(yCenter+text.getLayoutBounds().getHeight()/2);
        
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        Text ntext = new Text("Pressione Enter!");
        ntext.setFont(text.getFont());
        
        float nx = (float) (xCenter-ntext.getLayoutBounds().getWidth()/2);
        float ny = (float) (text.getY()+2*ntext.getLayoutBounds().getHeight());
        
        g.fillText(ntext.getText(), nx, ny);
        g.strokeText(ntext.getText(),nx, ny);
        
        g.restore();
    }

    @Override
    public void draw(GraphicsContext g) {
        for(Bala b: balas) {
            b.draw(g);
        }
        
        for(Bolinha b: bolinhas) {
            b.draw(g);
        }
        
        renderMensagem(g);
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
