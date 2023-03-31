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
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Contador;
import projeto1.entidades.elementos.Ponto;
import projeto1.graficos.GameLoop;
import projeto1.graficos.Pincel;
import projeto1.graficos.objetosGraficos.Animacao;
import projeto1.graficos.objetosGraficos.ElementosMapa;

/**
 *
 * @author Vitor Rinaldini
 */
public class Fase extends Cena{

    private Tabuleiro tab;
    private Contador contador;
    private ArrayList<ElementosMapa> elementosMapa;
    private ArrayList<Animacao> animes;
    private ArrayList<Ponto> pontos;
    
    private Cabecalho cabecalho;
    private Rodape rodape;
    
    private TelaWin telawin;
    private TelaLose telalose;
    private TelaPause telaPause;
    
    private GameLoop loop;
    
    private boolean pause = false;
    public Fase(InputKeyController teclado, Tabuleiro tab, GameLoop loop) {
        super(teclado);
        this.tab = tab;
        elementosMapa = new ArrayList();
        animes = new ArrayList();
        this.loop = loop;
    }
    
    public void renderTabuleiro(Pincel pincel) {
        for(ElementosMapa e: elementosMapa) {
            pincel.save();
            if(!e.isFinish()) pincel.drawAnime((Animacao) e);
            pincel.restore();
        }
        
        for(Animacao e: pontos) {
            pincel.save();
            if(!e.isFinish()) pincel.drawAnime((Animacao) e);
            pincel.restore();
        }
        
        for(Animacao a: animes) {
            pincel.save();
            if(!a.isFinish()) pincel.drawAnime(a);
            pincel.restore();
        }
    }
    
    public void renderTelaWin(Pincel pincel) {
        GraphicsContext g = pincel.getGrapics();
        g.save();
        telawin.draw(g);
        g.restore();
    }
    
    public void renderContador(Pincel pincel) {
        GraphicsContext g = pincel.getGrapics();
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
        
        Text text = new Text(xCenter, yCenter, "O Jogo começará em:");
        text.setFont(new Font("Verdana", raio/8));
        text.setX(xCenter-text.getLayoutBounds().getWidth()/2);
        text.setY(yCenter-text.getLayoutBounds().getHeight()/2);
        
        g.setFont(text.getFont());
        g.setStroke(Color.WHITE);
        g.setFill(Color.DARKGREEN);
        
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        text.setText(String.format("%.02f", contador.getContagem())+" s");
        text.setX(xCenter-text.getLayoutBounds().getWidth()/2);
        text.setY(yCenter+text.getLayoutBounds().getHeight()/2);
        
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        g.restore();
    }

    @Override
    public void render(Pincel pincel) {
        renderTabuleiro(pincel);
        if(contador.isAlive())renderContador(pincel);
        else if(isStarted() && !tab.isPlaying()) {
            if(tab.ganhou()) {
                renderTelaWin(pincel);
            } else {
                telalose.draw(pincel.getGrapics());
            }
        } else if(pause) {
            telaPause.draw(pincel.getGrapics());
        }
    }

    @Override
    public void start() {
        tab.setPlaying(false);
        contador = new Contador();
        contador.setContagem(3.5f);
        contador.setErro(0.01f);
        contador.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void pause() {
       if(pause) {
           pause = false;
       } else {
           pause = true;
           telaPause.reusar();
       } 
    }
    
    public void updateTabuleiro(double deltaTime) {
        tab.update(deltaTime);
        
        for(ElementosMapa e: elementosMapa) {
            if(!e.isFinish())e.update(deltaTime);
        }
        
        for(Animacao a: animes) {
            if(!a.isFinish())a.update(deltaTime);
        }
        
        if (tab.getJogador().isPoder()) {
               tab.getJogador().usarPoder((float) deltaTime);
        }
    } 

    @Override
    public void update(double deltaTime) {
        if(getTeclado().isPausado() && tab.isPlaying()) pause();
        if(tab.isPlaying() && !pause) {
            updateTabuleiro(deltaTime);
        } else if(!pause){
          if(contador.isZero() && !isStarted()) {
              tab.setPlaying(true);
              super.start();
          } else if(isStarted()){
              if(tab.ganhou()) {
                  telawin.update(deltaTime);
              } else {
                  telalose.update(deltaTime);
              }
          }
        }
        if(pause) {
            telaPause.update(deltaTime);
        }
        
    }

    @Override
    public void prepare() {
        elementosMapa.add((ElementosMapa) tab.getTabuleiroMontado());
        pontos = tab.getPontos();
        
        for(Animacao e: tab.getFantasmas()) {
            animes.add(e);
        }
        animes.add(tab.getJogador());
        
        for(ElementosMapa e : elementosMapa) {
            e.refresh(tab.getMapa().getRaio());
        }
        
        for(Animacao e : pontos) {
            e.refresh(tab.getMapa().getRaio());
        }
        
        for(Animacao e : animes) {
            e.refresh(tab.getMapa().getRaio());
        }
        
        telawin = new TelaWin(tab.getTabuleiroMontado().getRealX()+tab.getMapa().getWidth()/2f, tab.getTabuleiroMontado().getRealY()+tab.getMapa().getHeight()/2f, tab.getMapa().getHeight()/2f-5*tab.getMapa().getRaio(), tab, getTeclado());      
        telalose = new TelaLose(tab.getTabuleiroMontado().getRealX()+tab.getMapa().getWidth()/2f, tab.getTabuleiroMontado().getRealY()+tab.getMapa().getHeight()/2f, tab.getMapa().getHeight()/2f-5*tab.getMapa().getRaio(), tab, getTeclado(), loop);
    
        cabecalho = new Cabecalho(tab.getMapa().getWidth()/2+tab.getTabuleiroMontado().getX(), tab);
        animes.add(cabecalho);
        
        rodape = new Rodape(tab.getMapa().getWidth()/2+tab.getTabuleiroMontado().getX(), tab);
        animes.add(rodape);
        
        telaPause = new TelaPause(tab.getTabuleiroMontado().getRealX()+tab.getMapa().getWidth()/2f, 
                tab.getTabuleiroMontado().getRealY()+tab.getMapa().getHeight()/2f, 
                getTeclado(), 
                tab, this, loop);
        
        telaPause.prepare();
        
    }

    @Override
    public void finalize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
