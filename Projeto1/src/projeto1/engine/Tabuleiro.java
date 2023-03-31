/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import java.util.ArrayList;
import projeto1.engine.grafo.Caminho;
import projeto1.engine.grafo.Grafo;
import projeto1.engine.grafo.Node;
import projeto1.engine.niveis.Nivel;
import projeto1.engine.niveis.Nivel1;
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

/**
 *
 * @author Vitor Rinaldini
 */
public class Tabuleiro {
    /**
     * Classe que 'amarra' tudo, ou seja, ela é responsável pelo mapa, pelo jogador e por tudos os tipos de entidade e ponto.
     * @author Vitor Rinaldini
     */
    
    private final Grafo grafo;
    private final Mapa mapa;
    private final ArrayList<Ponto> pontos;
    private final ArrayList<Ponto2f> caminhos;
    private final PacMan jogador;
    private final ArrayList<Fantasma> fantasmas;
    private final ArrayList<Ponto2f> porteiras;
    private final Nivel nivel;
    
    private boolean jogando;
    
    private int contadorPacDots;
    
    /**
     * Construtor do tabuleiro.
     * @param player Recebe o jogador
     * @param nivel Recebe o nivel que será usado na construção.
     */
    public Tabuleiro(Player player, Nivel nivel) {
        mapa = new Mapa(nivel.getFILE_NAME());
        
        grafo = new Grafo(mapa.getXMax(), mapa.getYMax(), mapa);
        varrerEGerarGrafo();
        
        pontos = mapa.getPontosEPirulas(this);
        caminhos = mapa.getCaminhos();
        porteiras = mapa.getPorteiras();  
        this.nivel = nivel;
        
        jogador = new PacMan(0, 0, this, player);
        
        fantasmas = new ArrayList();
        fantasmas.add(new Blinky(0, 0, this, 3));
        fantasmas.add(new Clyde(0, 0, this, 3));
        fantasmas.add(new Inky(0, 0, this, 3)); 
        fantasmas.add(new Pinky(0, 0, this, 3));
        
        resetTabuleiro();
        
        contadorPacDots = 0;
        jogando = true;
    }
    
    /**
     * Resata as entidades do tabuleiro.
     */
    public void resetTabuleiro() {
        Ponto2f randomSpawnPlayer = mapa.getRandomSpawnPlayer();
        jogador.setX(randomSpawnPlayer.getX());
        jogador.setY(randomSpawnPlayer.getY());
        int contador = 0;
        for(Fantasma fantasma: fantasmas) {
            fantasma.setDest(null);
            Ponto2f randomSpawn = mapa.getRandomSpawnFantasma();
            fantasma.setX(randomSpawn.getX());
            fantasma.setY(randomSpawn.getY());
            if(contador == 0) fantasma.prender(1);
            else fantasma.prender((int) (5*Math.pow(2, contador)));
            contador++;
        }
        
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
     * Imprime o tabuleiro no console.
     */
    public void printTabuleiro() {
        char result[][] = new char[mapa.getXMax()][mapa.getYMax()];
        for(int i = 0; i < mapa.getXMax(); i++) {
            for(int j = 0; j < mapa.getYMax(); j++) {
                result[i][j] = mapa.getChar(i, j);
            }
        }
        
        for(Ponto ponto: pontos) {
            result[(int) ponto.getX()][(int) ponto.getY()] = ponto.getImg();
        }
        
        for(Fantasma fantasma: fantasmas) {
            result[(int) fantasma.getX()][(int) fantasma.getY()] = fantasma.getCor();
        }
        
        result[(int) jogador.getX()][(int) jogador.getY()] = jogador.getCharImag();
        
        for(int i = 0; i < mapa.getXMax(); i++) {
            System.out.print("\t");
            for(int j = 0; j < mapa.getYMax(); j++) {
                System.out.print(result[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Confere se há um ponto na posição do jogador. Se sim, ele pega tal ponto.
     */
    private void conferirPegarPonto() {
        for(Ponto ponto: pontos) {
            if(ponto.getX() == jogador.getX() && ponto.getY() == jogador.getY()) {
                jogador.addPontos(ponto.getValor());
                caminhos.add(new Ponto2f(ponto.getX(), ponto.getY()));
                
                if(ponto instanceof PacDots) {
                    contadorPacDots++;
                    for(Fantasma fantasma: fantasmas) {
                        if(fantasma instanceof Blinky && !jogador.isPoder() && fantasma.isVivo()) ((Blinky) fantasma).updateVelocidade(contadorPacDots);
                    }
                }
                else if(ponto instanceof Pilula) {
                    jogador.pegarPoder(nivel);
                    resetFantasmas();
                }
                
                pontos.remove(ponto);
                if(pontos.size() == 0) setPlaying(false);
                
                return;
            }
        }
        
    }
    
    /**
     * Imprime os fantasmas.
     */
    public void printFantasmas() {
        System.out.println("Temos: "+fantasmas.size()+" fanstasmas!");
        for(Fantasma fantasma: fantasmas) {
            fantasma.print();
        }
    }
    
    /**
     * Atualiza as Posições dos fantasmas.
     */
    private void updateFantasmas() {
        for(Fantasma fantasma: fantasmas) {
            fantasma.updateFantasma();
            boolean abrirPorteira = (!fantasma.isPresa() && mapa.isSpawnMonstros(Math.round(fantasma.getX()),Math.round(fantasma.getY())));
            if(abrirPorteira)grafo.abrirPorteiras(porteiras);
            fantasma.mover(1000);
            if(abrirPorteira)grafo.fecharPorteiras(porteiras);
        }
    }
    
    /**
     * Reseta a busca dos fantasmas.
     */
    public void resetFantasmas() {
        for(Fantasma fantasma: fantasmas) {
            if(fantasma.isVivo()) {
                if(!mapa.isPorteira(Math.round(fantasma.getX()), Math.round(fantasma.getY())))fantasma.setDest(null);
                
                if(jogador.isPoder()) fantasma.setVelocidadeToReduzida();
                else fantasma.setVelocidadeToNormal();
            } 
        }
    }
    
    /**
     * Colisção do Jogador com o Fantasma.
     */
    public void detectarColisao() {
        for(Fantasma fantasma: fantasmas) {
            if(jogador.isIgualAproximado(fantasma)) {
                if(jogador.isPoder() && fantasma.isVivo()) {
                    fantasma.morrer();
                    jogador.comerFantasma();
                } else if(fantasma.isVivo()){
                    jogador.morrer();
                    if(jogador.getVidas() >= 0) {
                        jogador.viver();
                        resetTabuleiro();
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
            fruta = new Cereja(randomPlace.getX(), randomPlace.getY());
        } else if(nivel.getFrutaTipo() == Morango.class) {
            fruta = new Morango(randomPlace.getX(), randomPlace.getY());
        } else {
            fruta = new Laranja(randomPlace.getX(), randomPlace.getY());
        }
        fruta.setVALOR(fruta.getValor()+((int) (nivel.getNivel()/3))*600f);
        pontos.add(fruta);
        nivel.setMinDotsSpawnado(true);
        contadorPacDots = 0;
    }
    
    /**
     * Atualiza o tabuleiro.
     */
    public void update() {
        conferirPegarPonto();
        updateFantasmas();
        detectarColisao();
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
    
    /**
     * Imprime o caminho de busca de um fantasma.
     * Método da entrega 1.
     */
    public void printTeste1() {
        char result[][] = new char[mapa.getXMax()][mapa.getYMax()];
        for(int i = 0; i < mapa.getXMax(); i++) {
            for(int j = 0; j < mapa.getYMax(); j++) {
                result[i][j] = mapa.getChar(i, j);
            }
        }
        
        Fantasma fantasma = fantasmas.get(0);
        result[(int) fantasma.getX()][(int) fantasma.getY()] = fantasma.getCor();
        
        if(fantasma.getCaminho() != null && fantasma.getCaminho().size() > 1) {
            Caminho caminho = fantasma.getCaminho();
            for(int i = 1; i < caminho.size(); i++) {
                System.out.println("\n");
                Node p1 = caminho.get(i-1);
                Node p2 = caminho.get(i);
                if(p1.isBorda(p2, mapa.getXMax(), mapa.getYMax())) {
                    
                } else if(p1.getX() == p2.getX()) {
                   int delta = 1;
                   if(p1.getY() > p2.getY()) delta = -1;
                   int j = 1;
                   while (p1.getY()+delta*j != p2.getY()){
                       result[(int) p1.getX()][(int) (p1.getY()+delta*j)] = '*';
                       j++;
                   }
                } else {
                   int delta = 1;
                   if(p1.getX() > p2.getX()) delta = -1;
                   int j = 1;
                   while (p1.getX()+delta*j != p2.getX()) {
                       result[(int) (p1.getX()+delta*j)][(int) p1.getY()] = '*';
                       j++;
                   }
                }
                
                if(i != caminho.size())result[(int)p2.getX()][(int)p2.getY()] = '*';
            }
        }
        
        result[(int) jogador.getX()][(int) jogador.getY()] = jogador.getCharImag();
        
        for(int i = 0; i < mapa.getXMax(); i++) {
            System.out.print("\t");
            for(int j = 0; j < mapa.getYMax(); j++) {
                System.out.print(result[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Reseta o tabuleiro para a execução de um novo teste1.
     */
    public void resetPositionAndCaminhoToTeste() {
        Fantasma fantasma = fantasmas.get(0);
        fantasma.setDest(null);
        
        Ponto2f pontoFantasma = mapa.getRandomSpawnPlayer();
        
        Ponto2f pontoJogador;
        do {
            pontoJogador = mapa.getRandomSpawnPlayer();
        } while (pontoJogador.getX() == pontoFantasma.getX() && pontoJogador.getY() == pontoFantasma.getY());
        
        fantasma.setX(pontoFantasma.getX());
        fantasma.setY(pontoFantasma.getY());
        
        jogador.setX(pontoJogador.getX());
        jogador.setY(pontoJogador.getY());
        
        fantasma.proxDestino();

    }
}
