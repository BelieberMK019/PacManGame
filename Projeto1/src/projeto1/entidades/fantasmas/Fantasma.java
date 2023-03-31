/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.fantasmas;

import projeto1.engine.Tabuleiro;
import projeto1.engine.grafo.Caminho;
import projeto1.engine.grafo.Grafo;
import projeto1.engine.grafo.Node;
import projeto1.engine.uteis.Ponto2f;
import projeto1.entidades.Entidade;

/**
 *
 * @author Vitor Rinaldini
 */
public abstract class Fantasma extends Entidade{
    /**
     * Definição de um fantasma padrão.
     * @author Vitor Rinaldini
     */
    private char cor;
    private Caminho caminho;
    private Node dest;
    private Node origem;
    
    private int turnosPresoRestante = 20;
    
    private final char MORTO = 'o';
    private final char ALVO = 'A';
    
    private final int TIME_RESPAWN = 25;
    
    public static final float PONTOS_FANTASMA = 200f;
    
    /**
     * Construtor.
     * @param x
     * @param y
     * @param tab
     * @param cor
     * @param turnosPreso 
     */
    public Fantasma(float x, float y, Tabuleiro tab, char cor, int turnosPreso) {
        super(x, y, tab);
        setCor(cor);
        
        caminho = null;
        dest = null;
        origem = null;
        
        turnosPresoRestante = turnosPreso;
        if(turnosPresoRestante <= 0) setPresa(false);
        else setPresa(true);
        
        caminhoParaVoltar = null;
    }

    /**
     * get Quantidade de tempo que ele ainda ficará preso.
     * @return 
     */
    public int getTurnosPresoRestante() {
        return turnosPresoRestante;
    }

    /**
     * prender o fantasma
     * @param time 
     */
    public void prender(int time) {
        setPresa(true);
        turnosPresoRestante = time;
    }
    
    /**
     * diminuir  o tempo preso.
     */
    public void diminuirTempoPreso() {
        turnosPresoRestante--;
        if(turnosPresoRestante == 0) liberar();
    }
    
    /**
     * liberar fantasma.
     */
    public void liberar() {
        setPresa(false);
    }
    
    /**
     * alterar lugar de destino.
     * @param dest 
     */
    public void setDest(Node dest) {
        this.dest = dest;
    }
    
    /**
     * confere se ele está no destino.
     * @return 
     */
    public boolean isOnDestino() {
        if(dest == null) return true;
        return (isIgual(dest));
    }

    /**
     * Retorna o destino do fantasma;
     * @return 
     */
    public Node getDest() {
        return dest;
    }
    
    /**
     * altera a cor do fantasma.
     * @param cor 
     */
    public void setCor(char cor) {
        this.cor = cor;
    }

    /**
     * retorna a cor do fantasma.
     * @return 
     */
    public char getCor() {      
        if(!isVivo()) {
            return MORTO;
        } else if(getTabuleiro().getJogador().isPoder()){
            return ALVO;
        } else {
            return cor;
        }
    }
    
    /**
     * Retorna o caractere para impressão
     * @return 
     */
    @Override
    public char getCharImag() {
        return getCor();
    }
    
    /**
     * mover certa distancia.
     * @param dist 
     */
    public void moverStep(float dist) {
        if(getDest()== null) return;
        int fator = +1;
        if(origem.isBorda(dest, getTabuleiro().getMapa().getXMax(), getTabuleiro().getMapa().getYMax())) {
            fator = -1;
        }
        if(getX() == getDest().getX()) {
            if(getY() > getDest().getY()) {
                moverY(-dist*fator);
            } else {
                moverY(+dist*fator);
            }
        } else {
            if(getX() > getDest().getX()) {
                moverX(-dist*fator);
            } else {
                moverX(+dist*fator);
            }
        }
    }
    /**
     * mover várias vezes aos poucos até concluir a movimentação.
     * @param timeInMilli 
     */
    public void mover(long timeInMilli) {
        float dist = getVelocidade()*timeInMilli/1000.0f;
        float distToDest = 0f;
        do {
            if(isOnDestino()) proxDestino();
            if(dest != null) {
                distToDest = 0f;
                if (origem.isBorda(dest, getTabuleiro().getMapa().getXMax(), getTabuleiro().getMapa().getYMax())) {
                    distToDest = 1f;
                    moverStep(distToDest);
                    dist -= distToDest;
                } else {
                    distToDest = distancia(dest);
                    float distStep;
                    if (distToDest >= 1.0f && dist >= 1.0f) {
                        distStep = 1.0f;
                    } else if ((distToDest >= 1.0f && dist < 1.0f) || (distToDest < 1.0f && distToDest >= dist)) {
                        distStep = dist;
                    } else {
                        distStep = distToDest;
                    }

                    moverStep(distStep);
                    dist -= distStep;
                }
            }
        } while(dist > 0 && dest != null);
    }
    
    /**
     * Setar um destino de forma a fugir do jogador.
     */
    protected void fugir() {
        Grafo g = getTabuleiro().getGrafo();
        Caminho caminho = g.fazerBuscaLargDist(this, getTabuleiro().getJogador());
        g.adicionarEntidade(this);
        g.updateEntidade(this);
        if(caminho == null && caminho.size() <= 0 && caminho.getFirstNode().getQuantidadeConexoes() <= 1) buscar();
        else {
            Node dest = null;
            if(this.getQuantidadeConexoes() == 0) {
                //NAO DEVE ACONTECER
                dest = null;
            }
            if(this.getQuantidadeConexoes() == 1 && this.isIgual(this.getNode(0)) && getNode(0).getQuantidadeConexoes() > 1) {
                while(dest == null) {
                    Node filho = getNode(0);
                    int rand = (int) Math.round(Math.random() * (filho.getQuantidadeConexoes() - 1));
                    Node aux = filho.getNode(rand);
                    if (caminho.size() > 1 && aux != caminho.get(1)) {
                        dest = aux.getCopy();
                    } else dest = null;
                }
            } else {
                if(getNode(0) == caminho.get(1)) {
                    dest = getNode(1).getCopy();
                } else {
                    dest = getNode(0).getCopy();
                }
            }
            setDest(dest);
        }
        g.removerEntidade(this);
    }
    
    private Caminho caminhoParaVoltar;
    /**
     * Caminho fixo para voltar ao spawn.
     */
    protected void voltarSpawn() {
        if(caminhoParaVoltar == null) {
            Ponto2f random = getTabuleiro().getMapa().getRandomSpawnFantasma();
            getTabuleiro().getGrafo().abrirPorteiras(getTabuleiro().getPorteiras());
            caminhoParaVoltar = getTabuleiro().getGrafo().fazerBuscaLargDistToPonto(this, random).getCopiaCaminhoComCopiaNodes();
            getTabuleiro().getGrafo().fecharPorteiras(getTabuleiro().getPorteiras());
            if(caminhoParaVoltar != null && caminhoParaVoltar.size() > 1) setDest(caminhoParaVoltar.get(1));
        } else if(caminhoParaVoltar.size() > 2){
            for(int i = 1; i < caminhoParaVoltar.size(); i++) {
                if(caminhoParaVoltar.get(i).isIgual(this)) {
                    setDest(caminhoParaVoltar.get(i+1).getCopy());
                    return;
                }
            }
        }
    }
    
    /**
     * método que irá definir como o fantasma irá se movimentar.
     */
    protected abstract void buscar();
    
    /**
     * calcular o proximo Destino do fantasma.
     */
    public void proxDestino() {
        if(dest != null) origem = dest;
        if(origem == null) origem = this;
        
        if(!isVivo()) {
            if(getTabuleiro().getMapa().isSpawnMonstros(Math.round(getX()), Math.round(getY()))) {
                viver();
                caminhoParaVoltar = null;
                setPresa(true);
                setVelocidade(VELOCIDADE_NORMAL);
                turnosPresoRestante = TIME_RESPAWN;
            } else {
                voltarSpawn();
            }
        } else if(!getTabuleiro().getJogador().isPoder() || getTabuleiro().getMapa().isSpawnMonstros(Math.round(getX()), Math.round(getY()))) {
            buscar();
        } else {
            fugir();
        }
    }
    
    /**
     * Atualizar fantasma.
     */
    public void updateFantasma() {
        if(isPresa() && getTabuleiro().getMapa().isSpawnMonstros(Math.round(getX()), Math.round(getY()))) diminuirTempoPreso();
    }

    /**
     * Matar fantasma.
     */
    @Override
    public void morrer() {
        super.morrer();
        setDest(null);
        setPresa(true);
        setVelocidade(VELOCIDADE_ACELERADA);
    }

    /**
     * Metodo para a realização do Teste da entrega 1.
     * @param caminho 
     */
    public void setCaminho(Caminho caminho) {
        this.caminho = caminho;
    }

    /**
     * Metodo para a realização do Teste da entrega 1.
     * @param caminho 
     */
    public Caminho getCaminho() {
        return caminho;
    }
    
}
