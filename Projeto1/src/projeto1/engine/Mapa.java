/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import projeto1.engine.uteis.Ponto2f;
import projeto1.entidades.elementos.PacDots;
import projeto1.entidades.elementos.Pilula;
import projeto1.entidades.elementos.Ponto;

/**
 *
 * @author Vitor Rinaldini
 */
public class Mapa {
    /**
     * Classe responsável por carregar um arquivo de mapa.
     * @author Vitor Rinaldini
     */
    
    
    private String FILE_NAME;
    private int xMax;
    private int yMax;
    private char mapa[][];
    
    /**
     * Tenta carregar o mapa, dado nome do arquivo.
     * @param FILE_NAME Nome do arquivo.
     */
    public Mapa(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
        try {
            carregarMapa();
        } catch(IOException e) {
            System.out.println("ERRO na abertura do mapa");
            System.exit(1);
        }
    }
    
    /**
     * Segnificado de cada caractere do arquivo map.
     */
    private final char NADA = '0';
    private final char PAREDE = '1';
    private final char CAMINHO_COM_PONTO = '2';
    private final char CAMINHO_COM_PODER = '4';
    private final char CAMINHO = '3';
    private final char PORTEIRA_FANTASMAS = '9';
    private final char SPAWN_FANTASMAS = '5';
    
    /**
     * Método para conferir se há um ponto naquela posição.
     * @param i linha
     * @param j coluna
     * @return  ture, se for Ponto; false, caso contrario
     */
    private boolean isPonto(int i, int j) {
        return mapa[i][j] == CAMINHO_COM_PONTO;
    }
    
    /**
     * Método para conferir se há uma Pilula naquela posição.
     * @param i linha
     * @param j coluna
     * @return  ture, se for Pilula; false, caso contrario
     */
    private boolean isPoder(int i, int j) {
        return mapa[i][j] == CAMINHO_COM_PODER;
    }
    
    /**
     * Método que cria os pontos do tipo Pacdots e Pilulas.
     * @param tabuleiro
     * @return vetor de Pontos e Pilulas;
     */
    public ArrayList<Ponto> getPontosEPirulas(Tabuleiro tabuleiro) {
        ArrayList<Ponto> pontos = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(isPonto(i, j)) {
                    pontos.add(new PacDots(i, j));
                } else if(isPoder(i, j)) {
                    pontos.add(new Pilula(i, j, tabuleiro));
                }
            }
        }
        return pontos;
    }
    
    /**
     * Método que busca todos os locais que haverá um ponto ou uma pílula;
     * @return Vetor de pontos, sendo cada um uma localização.
     */
    public ArrayList<Ponto2f> getLocaisDePontosEPirulas() {
        ArrayList<Ponto2f> pontos = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(isPonto(i, j) || isPoder(i, j)) {
                    pontos.add(new Ponto2f(i, j));
                }
            }
        }
        return pontos;
    }
    
    /**
     * Retorna localização das porteiras que separam o spawn do fantasma com o restante do mapa
     * @return Vetor de Pontos2f, sendo cada um uma localização.
     */
    public ArrayList<Ponto2f> getPorteiras() {
        ArrayList<Ponto2f> porteiras = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(isPorteira(i, j)) {
                    porteiras.add(new Ponto2f(i, j));
                }
            }
        }
        return porteiras;
    }
    
    /**
     * Método que retorna a área equivalente ao spawn dos fantasmas.
     * @return vetor de posições do spawn.
     */
    public ArrayList<Ponto2f> getSpawnFantasmas() {
        ArrayList<Ponto2f> spawn = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(isSpawnMonstros(i, j)) {
                    spawn.add(new Ponto2f(i, j));
                }
            }
        }
        return spawn;
    }
    
    /**
     * Método que retorna a área equivalente ao Spawn do jogador
     * @return vetor de posições de spawn;
     */
    public ArrayList<Ponto2f> getSpawnPlayer() {
        ArrayList<Ponto2f> pontos = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(isPonto(i, j)) {
                    pontos.add(new Ponto2f(i, j));
                }
            }
        }
        return pontos;
    }
    
    /**
     * Retorna todoa os lugares em que uma entidade pode se mover.
     * @return vetor de posições de cada lugar
     */
    public ArrayList<Ponto2f> getCaminhos() {
        ArrayList<Ponto2f> pontos = new ArrayList();
        for(int i = 0; i < xMax; i++) {
            for(int j = 0; j < yMax; j++) {
                if(mapa[i][j] == CAMINHO) {
                    pontos.add(new Ponto2f(i, j));
                }
            }
        }
        return pontos;
    }
    
    /**
     * Método para spawnar um fantasma num lugar aleatório
     * @return Ponto aleatorio;
     */
    public Ponto2f getRandomSpawnFantasma() {
        ArrayList<Ponto2f> spawn = getSpawnFantasmas();
        int random = (int) Math.round((float) Math.random()*(spawn.size()-1f));
        return spawn.get(random);
    }
    
    /**
     * Método para spawnar o jogador num lugar aleatório
     * @return Ponto aleatorio;
     */
    public Ponto2f getRandomSpawnPlayer() {
        ArrayList<Ponto2f> spawn = getSpawnPlayer();
        int random = (int) Math.round((float) Math.random()*(spawn.size()-1f));
        return spawn.get(random);
    }
    
    /**
     * Metodo que irá ler o arquivo do mapa e carregar o mapa.
     * @throws IOException 
     */
    public void carregarMapa() throws IOException {
        FileReader arq = new FileReader(FILE_NAME);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = null;
        
        linha = lerArq.readLine();
        int iMax = Integer.parseInt(linha);
        xMax = iMax;
        linha = lerArq.readLine();
        int jMax = Integer.parseInt(linha);
        yMax = jMax;
        
        mapa = new char[iMax][jMax];
        int i = 0;
        do {
            linha = lerArq.readLine();
            for(int j = 0; linha != null && j < linha.length(); j++) {
                mapa[i][j] = linha.charAt(j);     
            }
            i++;
        }while (linha != null);
        arq.close();
    }
    
    /**
     * Método para setar um codigo do mapa.
     * @param c code
     * @param i linha
     * @param j coluna
     */
    public void setMapaCode(char c, int i, int j) {
        if(i < 0 || j < 0 || i >= xMax || j >= yMax) return;
        this.mapa[i][j] = c;
    }
    
    /**
     * Método para get um codigo do mapa.
     * @param i linha
     * @param j coluna
     * @return codigo daquela posição
     */
    public char getCode(int i, int j) {
        if(i < 0 || j < 0 || i >= xMax || j >= yMax) return ' ';
        return this.mapa[i][j];
    }
    
    /**
     * Método que converte um codigo em uma parte do mapa.
     * @param code
     * @return char que será desenhado na tela.
     */
    public char convertToMapa(char code) {
        switch(code) {
            case NADA:
                return ' ';
            case PAREDE:
                return '#';
            case CAMINHO_COM_PONTO:
                return ' ';
            case PORTEIRA_FANTASMAS:
                return '=';
            case CAMINHO:
                return ' ';
            case SPAWN_FANTASMAS:
                return ' ';
            case CAMINHO_COM_PODER:
                return ' ';
            default:
                return ' ';
        }
    }
    
    /**
     * Confere se aquela posição é spawn de fantasmas
     * @param i linha
     * @param j coluna
     * @return true, se verdadeiro; false, caso contrario.
     */
    public boolean isSpawnMonstros(int i, int j) {
        return mapa[i][j] == SPAWN_FANTASMAS;
    }
    
    public boolean isCaminho(int i, int j) {
        return mapa[i][j] == CAMINHO || mapa[i][j] == CAMINHO_COM_PONTO || mapa[i][j] == CAMINHO_COM_PODER || mapa[i][j] == SPAWN_FANTASMAS;
    }
    
    public boolean isParede(int i, int j) {
        return mapa[i][j] == PAREDE || mapa[i][j] == PORTEIRA_FANTASMAS;
    }
    
    /**
     * Confere se aquela posição fica próxima a uma porteira.
     * @param i linha
     * @param j coluna
     * @return 
     */
    public boolean isPerimetroPorteira(int i, int j) {
        return ( (i+1 < xMax && isPorteira(i+1, j)) || (i-1 > 0  && isPorteira(i-1, j)) );
    }
    
    /**
     * Confere se aquela posição é uma porteira
     * @param i linha
     * @param j coluna
     * @return true, se verdadeiro; false, caso contrario.
     */
    public boolean isPorteira(int i, int j) {
        return mapa[i][j] == PORTEIRA_FANTASMAS;
    }
    
    /**
     * Método que retorna um caractere de mapa.
     * @param i linha
     * @param j cioluna
     * @return 
     */
    public char getChar(int i, int j) {
        return convertToMapa(getCode(i, j));
    }
    
    /**
     * Get X Máximo do mapa.
     * @return 
     */
    public int getXMax() {
        return xMax;
    }

    /**
     * Get Y Máximo do mapa.
     * @return 
     */
    public int getYMax() {
        return yMax;
    }
    
}
