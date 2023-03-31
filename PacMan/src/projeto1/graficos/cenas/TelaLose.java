/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Ponto2f;
import projeto1.entidades.fantasmas.Blinky;
import projeto1.entidades.fantasmas.Clyde;
import projeto1.entidades.fantasmas.Inky;
import projeto1.entidades.fantasmas.Pinky;
import projeto1.graficos.GameLoop;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class TelaLose extends Ponto2f implements Animacao{
    private float raio = 1f;
    
    ArrayList<Animacao> animes;
    
    Tabuleiro tab;
    GameLoop gameloop;
    InputKeyController teclado;
    public TelaLose(float xCenter, float yCenter, float raio, Tabuleiro tabuleiro, InputKeyController teclado, GameLoop game) {
        super(xCenter, yCenter);
        this.raio = raio;
        this.tab = tabuleiro;
        animes = new ArrayList();
        this.teclado = teclado;
        this.gameloop = game;
        criarFantasmas();
    }
    
    public void criarFantasmas() {
        
        float xCenter = tab.getTabuleiroMontado().getX()+tab.getMapa().getWidth()/2f;
        float yCenter = tab.getTabuleiroMontado().getX()+tab.getMapa().getHeight()/2f;
        float raio = 15*tab.getMapa().getRaio();
        
        float x = xCenter-raio;
        float y = yCenter-raio/2f;
        float dx = (2*raio)/3f;
        
        animes.add(new Blinky(x, y, tab, 0, 0));
        x += dx;
        animes.add(new Inky(x, y, tab, 0));
        x += dx;
        animes.add(new Clyde(x, y, tab, 0));
        x += dx;
        animes.add(new Pinky(x, y, tab, 0));
        
        for(Animacao a: animes) {
            
            a.refresh(raio/3);
        }
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        for(Animacao a: animes) {
            a.update(deltaTime);
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
        
        g.fillRoundRect(xCenter-raio, yCenter-raio/2, 2*raio, raio, raioBorda, raioBorda);
        
        g.setGlobalAlpha(1.0);
        g.setLineWidth(4f);
        g.setStroke(Color.BLACK);
        
        g.strokeRoundRect(xCenter-raio, yCenter-raio/2, 2*raio, raio, raioBorda, raioBorda);
        
        g.setLineWidth(0.5f);
        g.setStroke(Color.WHITE);
        
        g.strokeRoundRect(xCenter-raio, yCenter-raio/2, 2*raio, raio, raioBorda, raioBorda);
        
        g.setLineWidth(1f);
        
        Text text = new Text(xCenter, yCenter, "GAME OVER!!");
        text.setFont(new Font("Verdana", raio/8));
        text.setX(xCenter-text.getLayoutBounds().getWidth()/2);
        text.setY(yCenter-text.getLayoutBounds().getHeight()/2);
        
        g.setFont(text.getFont());
        g.setStroke(Color.WHITE);
        g.setFill(Color.DARKGREEN);
        
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        float pontuacao = tab.getPlayer().getScoreAcumulado()+tab.getPlayer().getScore();
        text.setText("Pontuação Final: "+ pontuacao);
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
        
        if(teclado.isGo()) {
            tab.getLoop().mudarToLoad();
            tab.getLoop().createAndLoadMenuPrincipal();
            return;
        }
        
        float amplitude = 30;

        float angle = 2*30/3f;
        
        int i = 0;
        for(Animacao a: animes) {
            g.save(); 
            Affine rotate = new Affine();
            rotate.appendRotation(-amplitude, a.getRealX(), a.getRealY()); 
            rotate.appendRotation(+i*angle, a.getRealX(), a.getRealY());
            g.setTransform(rotate);
            
            a.draw(g);
            
            g.restore();
            i++;
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
