/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import projeto1.engine.grafo.Grafo;
import projeto1.engine.niveis.Nivel;
import projeto1.engine.uteis.Ponto2f;
import projeto1.entidades.PacMan;
import projeto1.entidades.Player;
import projeto1.entidades.elementos.frutinhas.Frutinha;
import projeto1.entidades.elementos.PacDots;
import projeto1.entidades.elementos.Pilula;
import projeto1.entidades.elementos.Ponto;
import projeto1.entidades.elementos.frutinhas.Cereja;
import projeto1.entidades.elementos.frutinhas.Laranja;
import projeto1.entidades.elementos.frutinhas.Morango;
import projeto1.entidades.fantasmas.Blinky;
import projeto1.entidades.fantasmas.Clyde;
import projeto1.entidades.fantasmas.Fantasma;
import projeto1.entidades.fantasmas.Inky;
import projeto1.entidades.fantasmas.Pinky;
import projeto1.graficos.GameLoop;
import projeto1.graficos.objetosGraficos.TabuleiroMontado;
import projeto1.graficos.objetosGraficos.TexturasGraficas;

/**
 *
 * @author Vitor Rinaldini
 */
public class Tabuleiro {
    /**
     * Classe que 'amarra' tudo, ou seja, ela é responsável pelo mapa, pelo jogador e por tudos os tipos de entidade e ponto.
     * @author Vitor Rinaldini
     */
    
    private Grafo grafo;
    private Mapa mapa;
    private ArrayList<Ponto> pontos;
    private ArrayList<Ponto2f> caminhos;
    private PacMan jogador;
    private Player player;
    private ArrayList<Fantasma> fantasmas;
    private ArrayList<Ponto2f> porteiras;
    private Nivel nivel;
    private TabuleiroMontado tab;
    private TexturasGraficas textura;
    private InputKeyController teclado;
    
    public TexturasGraficas getTextura() {
        return textura;
    }
    
    private boolean jogando = false;
    
    private int contadorPacDots;
    private GameLoop loop;

    public GameLoop getLoop() {
        return loop;
    }
    
    /**
     * Construtor do tabuleiro.
     * @param player Recebe o jogador
     * @param nivel Recebe o nivel que será usado na construção.
     */
    public Tabuleiro(TexturasGraficas textura, Player player, Nivel nivel, InputKeyController teclado, GameLoop gameloop) {
        this.loop = gameloop;
        this.textura = textura;
        this.teclado = teclado;
        this.nivel = nivel;
        
        this.player = player;
        mapa = new Mapa(nivel.getFILE_NAME());
        tab = new TabuleiroMontado(textura, 12, 50, 690, mapa, Color.BLACK);
        
    }
    
    public void prepare() {
        grafo = new Grafo(mapa.getXMax(), mapa.getYMax(), mapa);
        
        varrerEGerarGrafo();

        pontos = mapa.getPontosEPirulas(this);
        caminhos = mapa.getCaminhos();
        porteiras = mapa.getPorteiras(); 
        
        jogador = new PacMan(0, 0, this, player, teclado);
        
        fantasmas = new ArrayList();
        fantasmas.add(new Blinky(0, 0, this, 3, nivel.getFATOR_VELOCIDADE_BLINKY()));
        fantasmas.add(new Clyde(0, 0, this, 3));
        fantasmas.add(new Inky(0, 0, this, 3)); 
        fantasmas.add(new Pinky(0, 0, this, 3));
        
        resetTabuleiro();
        
        contadorPacDots = 0;
    }
    
    public TabuleiroMontado getTabuleiroMontado() {
        return tab;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Nivel getNivel() {
        return nivel;
    }
    
    /**
     * Resata as entidades do tabuleiro.
     */
    public void resetTabuleiro() {  
        Ponto2f randomSpawnPlayer = mapa.getRandomSpawnPlayer();
        Ponto2f im = mapa.getRealPosition((int)randomSpawnPlayer.getX(), (int) randomSpawnPlayer.getY(), tab.getRealX(), tab.getRealY());
        jogador.setX(im.getX());
        jogador.setY(im.getY());
        int contador = 0;
        for(Fantasma fantasma: fantasmas) {
            fantasma.setDest(null);
            Ponto2f randomSpawn = mapa.getRandomSpawnFantasma();
            Ponto2f real = mapa.getRealPosition((int)randomSpawn.getX(),(int) randomSpawn.getY(), getTabuleiroMontado().getRealX(), getTabuleiroMontado().getY());
            fantasma.setX(real.getX());
            fantasma.setY(real.getY());
            float Amplitude = nivel.getTEMPO_BASE_FATASMA_SPAWN();
            if(contador == 0) fantasma.prender(1);
            else fantasma.prender((int) (Amplitude*Math.pow(2, contador)));
            contador++;
        }
    }
    
    public void reestart() {
        loop.getCena().stop();
        loop.getCena().start();
    }
    
    public ArrayList<Fantasma> getFantasmas() {
        return fantasmas;
    }
    
    public ArrayList<Ponto> getPontos() {
        return  pontos;
    }
    
    /**
     * retorna a localizção das porteiras.
     * @return 
     */
    public ArrayList<Ponto2f> getPorteiras() {
        return porteiras;
    }
    
    /**
     * Gera os nodes do Grafo.
     */
    private void varrerMapaGerandoNodes() {
        for(int i = 0; i < mapa.getXMax(); i++) {
            for(int j = 0; j < mapa.getYMax(); j++) {
                if(mapa.isCaminho(i, j) || mapa.isSpawnMonstros(i, j)) {
                    grafo.adicionar(i, j);
                }
            }
        }
    }
    
    /**
     * Varre o grafo, gera os nodes, conecta e normaliza.
     */
    public void varrerEGerarGrafo() {
        varrerMapaGerandoNodes();
        grafo.conectarNosAdjacentesComBordas(mapa.getXMax(), mapa.getYMax());
        grafo.normalizar();
    }
    
    /**
     * Confere se há um ponto na posição do jogador. Se sim, ele pega tal ponto.
     */
    private void conferirPegarPonto() {
        Rectangle2D hitBoxJogador = jogador.getBox(mapa.getRaio());
        for(Ponto ponto: pontos) {
            Rectangle2D hitBoxPonto = ponto.getBox(mapa.getRaio());
            
            if(hitBoxJogador.intersects(hitBoxPonto)) {
                
                jogador.addPontos(ponto.getValor());
                caminhos.add(new Ponto2f(ponto.getX(), ponto.getY()));
                
                if(ponto instanceof PacDots) {
                    contadorPacDots++;
                    for(Fantasma fantasma: fantasmas) {
                        if(fantasma instanceof Blinky) {
                            ((Blinky) fantasma).updateVelocidade(contadorPacDots);
                            if(!jogador.isPoder() && fantasma.isVivo()) ((Blinky) fantasma).setVelocidadeToNormal();
                        }
                    }
                }
                else if(ponto instanceof Pilula) {
                    jogador.pegarPoder(nivel);
                    resetFantasmas();
                }
                ponto.finalizar();
                pontos.remove(ponto);
                if(pontos.size() == 0) setPlaying(false);
                
                return;
            }
        }
        
    }
    
    /**
     * Atualiza as Posições dos fantasmas.
     */
    private void updateFantasmas(double deltaTime) {
        for(Fantasma fantasma: fantasmas) {
            fantasma.updateFantasma((float) deltaTime);
            Ponto2f imaginary = mapa.getImaginaryPosition(fantasma.getX(), fantasma.getY(), tab.getX(), tab.getY());
            boolean abrirPorteira = (!fantasma.isPresa() && mapa.isSpawnMonstros((int)imaginary.getX(),(int) imaginary.getY()));    
            if(abrirPorteira)grafo.abrirPorteiras(porteiras);
            fantasma.mover(deltaTime);   
            if(abrirPorteira)grafo.fecharPorteiras(porteiras);
        }
    }
    
    /**
     * Reseta a busca dos fantasmas.
     */
    public void resetFantasmas() {
        for(Fantasma fantasma: fantasmas) {
            if(fantasma.isVivo()) {
                Ponto2f imaginary = mapa.getImaginaryPosition((fantasma.getX()), fantasma.getY(), tab.getX(), tab.getY());
                Ponto2f real = mapa.getRealPosition((int)imaginary.getX(), (int)imaginary.getY(), tab.getX(), tab.getY());
                if(!mapa.isPorteira((int)imaginary.getX(),(int)imaginary.getY()))fantasma.setDest(null);
                fantasma.setPostion(real);
                if(jogador.isPoder()) fantasma.setVelocidadeToReduzida();
                else fantasma.setVelocidadeToNormal();
            } 
        }
    }
    
    /**
     * Colisção do Jogador com o Fantasma.
     */
    public void detectarColisao() {
        Rectangle2D hitBoxJogador = getJogador().getBox(mapa.getRaio());
        for(Fantasma fantasma: fantasmas) {
            Rectangle2D hitBoxFantasma = fantasma.getBox(mapa.getRaio());
            if(hitBoxJogador.intersects(hitBoxFantasma)) {
                if(jogador.isPoder() && fantasma.isVivo()) {
                    fantasma.morrer();
                    jogador.comerFantasma();
                } else if(fantasma.isVivo()){
                    jogador.morrer();
                    if(jogador.getVidas() >= 0) {
                        jogador.viver();
                        resetTabuleiro();
                        reestart();
                    } else {
                        setPlaying(false);
                    }
                }
            }
        }
    }
    
    /**
     * Adiciona um Fruta ao tabuleiro, respeitando as regras.
     */
    public void adicionarFrutinha() {
        if(caminhos.size() <= 0) return;
        int random = (int) Math.round((float) Math.random()*(caminhos.size()-1f));
        Ponto2f randomPlace = caminhos.get(random);
        Frutinha fruta;
        if(nivel.getFrutaTipo() == Cereja.class) {
            fruta = new Cereja(randomPlace.getX(), randomPlace.getY(), this);
        } else if(nivel.getFrutaTipo() == Morango.class) {
            fruta = new Morango(randomPlace.getX(), randomPlace.getY(), this);
        } else {
            fruta = new Laranja(randomPlace.getX(), randomPlace.getY(), this);
        }
        fruta.setVALOR(fruta.getValor()+((int) (nivel.getNivel()/3))*600f);
        fruta.refresh(mapa.getRaio());
        pontos.add(fruta);
        nivel.setMinDotsSpawnado(true);
        contadorPacDots = 0;
    }
    
    /**
     * Atualiza o tabuleiro.
     */
    public void update(double deltaTime) {
        updateFantasmas(deltaTime);
        detectarColisao();
        conferirPegarPonto();
        if((!nivel.isMinDotsSpawnado() && contadorPacDots >= nivel.getMIN_DOTS_TO_SPAWN_FRUTINHA()) || 
                (nivel.isMinDotsSpawnado() && contadorPacDots >= nivel.getDELTA_DOTS_TO_SPAWN_FRUTINHA())) adicionarFrutinha();
        
    }
    
    /**
     * Get o mapa.
     * @return Mapa
     */
    public Mapa getMapa() {
        return mapa;
    }

    /**
     * Get o grafo
     * @return Grafo 
     */
    public Grafo getGrafo() {
        return grafo;
    }
    
    /**
     * Get o jogador
     * @return PacMan
     */
    public PacMan getJogador() {
        return jogador;
    }
    
    /**
     * Atribui se a fase ainda está em operação.
     * @param play 
     */
    public void setPlaying(boolean play) {
        this.jogando = play;
    }
    
    /**
     * Confere se a fase ainda está em operação.
     * @return 
     */
    public boolean isPlaying() {
        return jogando;
    }
    
    public boolean ganhou() {
        if(getJogador().isVivo()) {
            return true;
        } else {
            return false;
        }
    }

    
}
