/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import projeto1.engine.Game;
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.niveis.Nivel;
import projeto1.engine.uteis.Loader;
import projeto1.entidades.Player;
import projeto1.graficos.cenas.Cena;
import projeto1.graficos.cenas.Fase;
import projeto1.graficos.cenas.LoadingCena;
import projeto1.graficos.cenas.MenuPrincipal;
import projeto1.graficos.objetosGraficos.TexturasGraficas;

/**
 *
 * @author Vitor Rinaldini
 */
public class GameLoop {
    private Game game;
    private Timeline gameloop;
    private Canvas canvas;
    private Pincel pincel;
    private int FPS_LIMITE;
    private int FPS_ATUAL;
    
    private boolean running;
    
    private LoadingCena telaLoad;
    private Cena cena;
    
    private InputKeyController teclado;
    
    public GameLoop(Canvas canvas, InputKeyController teclado) {
        mudarToLoad();
        this.canvas = canvas;
        this.pincel = new Pincel(canvas);
        
        this.FPS_LIMITE = 60;
        this.running = false;
        
        this.FPS_ATUAL = 0;
        
        this.teclado = teclado;
        
         game = new Game();
        
        telaLoad = new LoadingCena(teclado);
        telaLoad.prepare();
        
        createAndLoadMenuPrincipal();
        
        gameloop = createLoop();
    }

    public Game getGame() {
        return game;
    }
    
    private Loader loader;
    private boolean carregando = false;
    public void createAndLoadFase(Player player, int numeroNivel, TexturasGraficas textura) {
        Nivel nivel = Nivel.createNivel(numeroNivel);
        Tabuleiro tab = new Tabuleiro(textura, player, nivel, teclado, this);
        this.cena = new Fase(teclado, tab, this);
        loader = new Loader(cena, tab);
        loader.start();
        carregando = true;
    }
    
    public void createAndLoadMenuPrincipal() {
        this.cena = new MenuPrincipal(teclado, game, this);
        loader = new Loader(cena, null);
        loader.start();
        carregando = true;
    }
    
    public Cena getCena() {
        return cena;
    }
    
    public void mudarToLoad() {
        carregando = true;
    }
    
    public Timeline createLoop() {
        Duration duration = Duration.millis(1000/FPS_LIMITE);
        KeyFrame oneFrame = new KeyFrame(duration, this::run);
        Timeline time = new Timeline(FPS_LIMITE, oneFrame);
        time.setCycleCount(Animation.INDEFINITE);
        return time;
    }
    
    public void play() {
        long time = System.nanoTime();
        now = newNow = timer = time;
        gameloop.playFromStart();
        running = true;
    }
    
    public void stop() {
        gameloop.stop();
        running = false;
    }
    
    
    private int frames = 0;
    private long now, newNow, timer;
    
    private void run(Event e) {
        now = System.nanoTime();
        double deltaInSeconds = (now - newNow) / 1000000000.0;
        if (deltaInSeconds < 0) {
            deltaInSeconds = 0;
        }
        
        
        try {
            if (running) {
                update(deltaInSeconds);
                render();
                frames++;
            }
        } catch(Exception er) {
            System.out.println("ERRO de GameLoop: " +er.getMessage());
        }

        newNow = now;
        long timeAtual = System.nanoTime();
        if (timeAtual - timer >= 1000000000) {
            timer += 1000000000;
            FPS_ATUAL = frames;
            frames = 0;
        }
    }
    
    public void update(double deltaTime) {
        if(carregando) {
            telaLoad.update(deltaTime);
            if(!loader.isAlive() || loader == null) {
                carregando = false;
                cena.start();
            }
        } else {
            cena.update(deltaTime);
        }
    }
    
    public void render() {
        pincel.refresh();
        pincel.dispose();
        
        pincel.save();
        if(carregando) telaLoad.render(pincel);
        else cena.render(pincel);
        pincel.restore();
        
        if(teclado.exibir_fps) renderFps();
    }
    
    public void renderFps() {
        pincel.drawString(FPS_ATUAL+" FPS", 5, 20, Color.YELLOW, new Font("Verdana", 20));
    }
}
