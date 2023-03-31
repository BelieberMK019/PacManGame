/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.fantasmas;

import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
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
    private Color cor;
    private String name;
    private Caminho caminho;
    private Node dest;
    private Node origem;
    
    private float timeRestantePreso = 20;
    
    private final char MORTO = 'o';
    private final char ALVO = 'A';
    
    private final float TIME_RESPAWN = 15;
    
    public static final float PONTOS_FANTASMA = 200f;
    
    /**
     * Construtor.
     * @param x
     * @param y
     * @param tab
     * @param cor
     * @param turnosPreso 
     */
    public Fantasma(float x, float y, Tabuleiro tab, Color cor, float turnosPreso, String nome) {
        super(x, y, tab);
        setCor(cor);
        
        caminho = null;
        dest = null;
        origem = null;
        
        timeRestantePreso = turnosPreso;
        if(timeRestantePreso <= 0) setPresa(false);
        else setPresa(true);
        
        this.name = nome;
        caminhoParaVoltar = null;
        
        Random rand = new Random();
        this.time = rand.nextInt(5);
    }

    /**
     * get Quantidade de tempo que ele ainda ficará preso.
     * @return 
     */
    public float getTimePresoRestante() {
        return timeRestantePreso;
    }

    /**
     * prender o fantasma
     * @param time 
     */
    public void prender(float time) {
        setPresa(true);
        timeRestantePreso = time;
    }
    
    /**
     * diminuir  o tempo preso.
     */
    public void diminuirTempoPreso(float deltaTime) {
        timeRestantePreso -= deltaTime;
        if(timeRestantePreso <= 0) liberar();
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
        return isIgual(dest);
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
    public void setCor(Color cor) {
        this.cor = cor;
    }

    /**
     * retorna a cor do fantasma.
     * @return 
     */
    public Color getCor() {      
        if(!isVivo()) {
            return null;
        } else if(getTabuleiro().getJogador().isPoder()){
            return Color.BLUE;
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
        return 'q';
    }
    
    /**
     * mover certa distancia.
     * @param dist 
     */
    public void moverStep(float dist) {
        if(getDest()== null) return;
        
        Ponto2f backupFanrtasma = new Ponto2f(getX(), getY());
        Ponto2f fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        
        setPostion(fantasmaImaginary);
        
        Ponto2f distImaginary = getTabuleiro().getMapa().getImaginaryPosition(dest.getX(), dest.getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        
        int fator = +1;
        if(origem.isBorda(new Node(distImaginary.getX(), distImaginary.getY()), getTabuleiro().getMapa().getXMax(), getTabuleiro().getMapa().getYMax())) {
            fator = -1;
        }
        
        setPostion(backupFanrtasma);
        if(getX() == getDest().getX()) {
            if(getY() > getDest().getY()) {
                setPostion(backupFanrtasma);
                moverY(-dist*fator);
                direcao = CIMA;
            } else {
                setPostion(backupFanrtasma);
                moverY(+dist*fator);
                direcao = BAIXO;
            }
        } else {
            if(getX() > getDest().getX()) {
                setPostion(backupFanrtasma);
                moverX(-dist*fator);
                direcao = ESQUERDA;
            } else {
                setPostion(backupFanrtasma);
                moverX(dist*fator);
                direcao = DIREITA;
            }
        }
    }
    /**
     * mover várias vezes aos poucos até concluir a movimentação.
     * @param timeInMilli 
     */
    public void mover(double time) {
        float dist = (float) (getVelocidade()*time);
        float distToDest = 0f;
        
        Ponto2f backupFanrtasma = new Ponto2f(getX(), getY());
        Ponto2f fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        
        try {
            int vezes = 0;
            do {
                setPostion(backupFanrtasma);
                
                if (isOnDestino()) {
                    proxDestino();
                }
                setPostion(fantasmaImaginary);
                if (dest != null) {
                    setPostion(backupFanrtasma);
                    Ponto2f distImaginary = getTabuleiro().getMapa().getImaginaryPosition(dest.getX(), dest.getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
                    distToDest = 0f;
                    if (origem.isBorda(new Node(distImaginary.getX(), distImaginary.getY()), getTabuleiro().getMapa().getXMax(), getTabuleiro().getMapa().getYMax())) {
                        distToDest = 1f;
                        setPostion(backupFanrtasma);
                        moverStep(distToDest);
                        dist -= distToDest;
                    } else {
                        float limite = getTabuleiro().getMapa().getRaio();
                        distToDest = distancia(dest);
                        float distStep;
                        if (distToDest >= limite && dist >= limite) {
                            distStep = limite;
                        } else if ((distToDest >= limite && dist < limite) || (distToDest < limite && distToDest >= dist)) {
                            distStep = dist;
                        } else {
                            distStep = distToDest;
                        }
                        if((int) Math.round(distToDest) != 0) {
                            moverStep(distStep);
                            dist -= distStep;
                        } else {
                            setX(dest.getX());
                            setY(dest.getY());
                        }
                    }
                    backupFanrtasma = new Ponto2f(getX(), getY());
                    fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
                }
                setPostion(backupFanrtasma);
                
                vezes++;
                if(vezes > 50) throw new Exception("Aquele bug aconteceu! :-(");
            } while (dist > 0 && dest != null);
        } catch(Exception e) {
            setDest(null);
            setPostion(backupFanrtasma);
            System.out.println("Erro: "+e.getMessage());
        }
        
        
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
            boolean encontrado = false;
            for(int i = 1; i < caminhoParaVoltar.size() && !encontrado; i++) {
                if(caminhoParaVoltar.get(i).isIgual(this)) {
                    setDest(caminhoParaVoltar.get(i+1).getCopy());
                    encontrado = true;
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
        if(dest != null) {
            dest.setPostion(getTabuleiro().getMapa().getImaginaryPosition(dest.getX(), dest.getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY()));
         }
        
        if(dest != null) origem = dest;
        if(origem == null) origem = this;
        
        Ponto2f backupFanrtasma = new Ponto2f(getX(), getY());
        Ponto2f fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        
        Ponto2f backupJogador = new Ponto2f(getTabuleiro().getJogador().getX(), getTabuleiro().getJogador().getY());
        Ponto2f jogadorImaginary = getTabuleiro().getMapa().getImaginaryPosition(getTabuleiro().getJogador().getX(),  getTabuleiro().getJogador().getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        
        setPostion(fantasmaImaginary);
        getTabuleiro().getJogador().setPostion(jogadorImaginary);

        try{
            if (!isVivo()) {
                if (getTabuleiro().getMapa().isSpawnMonstros(Math.round(getX()), Math.round(getY()))) {
                    dest = null;
                    viver();
                    caminhoParaVoltar = null;
                    setPresa(true);
                    setVelocidadeToNormal();
                    timeRestantePreso = TIME_RESPAWN;
                } else {
                    voltarSpawn();
                }
            } else if (!getTabuleiro().getJogador().isPoder() || getTabuleiro().getMapa().isSpawnMonstros(Math.round(getX()), Math.round(getY()))) {
                buscar();
            } else {
                fugir();
            }
        } catch (Exception e) {
            System.out.println("Erro: "+e.getMessage());
        }
        
        if(dest != null) {
            dest.setPostion(getTabuleiro().getMapa().getRealPosition((int)dest.getX(),(int) dest.getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY()));
         }
        
        setPostion(backupFanrtasma);
        getTabuleiro().getJogador().setPostion(backupJogador);
        
        
    }
    
    /**
     * Atualizar fantasma.
     */
    public void updateFantasma(float deltaTime) {
         Ponto2f fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        if(isPresa() && getTabuleiro().getMapa().isSpawnMonstros((int)fantasmaImaginary.getX(), (int)fantasmaImaginary.getY())) diminuirTempoPreso(deltaTime);
    }

    /**
     * Matar fantasma.
     */
    @Override
    public void morrer() {
        Ponto2f fantasmaImaginary = getTabuleiro().getMapa().getImaginaryPosition(getX(), getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        Ponto2f fantasmareal = getTabuleiro().getMapa().getRealPosition((int)fantasmaImaginary.getX(), (int)fantasmaImaginary.getY(), getTabuleiro().getTabuleiroMontado().getX(), getTabuleiro().getTabuleiroMontado().getY());
        setPostion(fantasmareal);
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

    @Override
    public boolean isFinish() {
        return false;
    }

    public static final int DIREITA = 0;
    public static final int ESQUERDA = 1;
    public static final int CIMA = 2;
    public static final int BAIXO = 3;
    int direcao = BAIXO;
    
    private float fase = 0.0f;
    private final float dFase = 5f;
    
    private boolean piscando = false;
    private float fasePiscar = 0f;
    private double time = 0;
    private float raio = 1f;
    @Override
    public void update(double deltaTime) {
        fase += deltaTime*dFase;
        if(fase > 2*Math.PI) fase -= 2*Math.PI;
        
        if(!piscando) time += deltaTime;
        
        if(time > 5) {
            time = 0;
            piscando = true;
            fasePiscar = 0;
        }
        
        
        if(piscando) {
            fasePiscar += 10f*deltaTime;
            if(fasePiscar >= Math.PI) {
                piscando = false;
            }
        }
    }

    public void drawCorpo(GraphicsContext g, float xCenterCabeça, float yCenterCabeça) {
        
        float altura = raio;
        float raioCabeca = raio;
             
        g.setFill(cor);
        g.setStroke(Color.BLACK);
        g.beginPath();
        g.moveTo(xCenterCabeça-raioCabeca, yCenterCabeça);
        g.lineTo(xCenterCabeça-raioCabeca, yCenterCabeça+altura);
        
        float auxX = xCenterCabeça-raioCabeca;
        float dx = 2*raioCabeca/6;
        float auxY = yCenterCabeça+altura;
        float raioSaia = dx/2;
        g.arc(auxX, auxY, raioSaia, raioSaia, 270, 90);
        g.arc(auxX+dx, auxY, raioSaia, raioSaia, 180, -180);
        g.arc(auxX+2*dx, auxY, raioSaia, raioSaia, 180, 180);
        g.arc(auxX+3*dx, auxY, raioSaia, raioSaia, 180, -180);
        g.arc(auxX+4*dx, auxY, raioSaia, raioSaia, 180, 180);
        g.arc(auxX+5*dx, auxY, raioSaia, raioSaia, 180, -180);
        g.arc(auxX+6*dx, auxY, raioSaia, raioSaia, 180, 90);
        
        g.lineTo(xCenterCabeça+raioCabeca, yCenterCabeça);
        g.arc(xCenterCabeça, yCenterCabeça, raio, raio, 0, 180);
        g.closePath();
        g.fill();
        g.stroke();
    }
    
    public void drawOlhos(GraphicsContext g, float xCenterCabeça, float yCenterCabeça) {
        
        float raioCabeca = raio;
        float raioOlho = 7*raio/24;
        float raioCentroOlho = 2*raioOlho/5;
        
        float distOlhos = raioCabeca;
        float distOlhoEsqCentro = distOlhos/2;
        
        float correcao = -raioOlho;
        
        float dMax = raioOlho/3;
        
        float dxDir = 0.0f;
        float dyDir = 0.0f;
        
        if(direcao == DIREITA) {
            dxDir = dMax;
        } else if(direcao == ESQUERDA) {
            dxDir = -dMax;
        } else if(direcao == CIMA) {
            dyDir = -dMax;
        } else if(direcao == BAIXO) {
            dyDir = dMax;
        }
        
        float xOlhoEsq = xCenterCabeça-distOlhoEsqCentro+dxDir;
        float yOlhoEsq = yCenterCabeça+correcao;
        
        float xOlhoDir = xOlhoEsq+distOlhos;
        float yOlhoDir = yOlhoEsq;
                
        g.setFill(Color.WHITE);
        g.setStroke(Color.BLACK);
        g.fillOval(xOlhoEsq - raioOlho, yOlhoEsq-raioOlho, 2*raioOlho, 2*raioOlho);
        g.strokeOval(xOlhoEsq - raioOlho, yOlhoEsq-raioOlho, 2*raioOlho, 2*raioOlho);
        
        g.setFill(Color.BLUE);
        g.setStroke(Color.BLACK);
        g.fillOval(xCenterCabeça-distOlhoEsqCentro-raioCentroOlho+2*dxDir, yCenterCabeça+correcao-raioCentroOlho+dyDir, 2*raioCentroOlho, 2*raioCentroOlho);
        g.strokeOval(xCenterCabeça-distOlhoEsqCentro-raioCentroOlho+2*dxDir, yCenterCabeça+correcao-raioCentroOlho+dyDir, 2*raioCentroOlho, 2*raioCentroOlho);
        
        
        g.setFill(Color.WHITE);
        g.setStroke(Color.BLACK);
        g.fillOval(xOlhoDir-raioOlho, yOlhoDir-raioOlho, 2*raioOlho, 2*raioOlho);
        g.strokeOval(xOlhoDir-raioOlho, yOlhoDir-raioOlho, 2*raioOlho, 2*raioOlho);
    
        g.setFill(Color.BLUE);
        g.setStroke(Color.BLACK);
        g.fillOval(xCenterCabeça-distOlhoEsqCentro-raioCentroOlho+distOlhos+2*dxDir, yCenterCabeça+correcao-raioCentroOlho+dyDir, 2*raioCentroOlho, 2*raioCentroOlho);
        g.strokeOval(xCenterCabeça-distOlhoEsqCentro-raioCentroOlho+distOlhos+2*dxDir, yCenterCabeça+correcao-raioCentroOlho+dyDir, 2*raioCentroOlho, 2*raioCentroOlho);
    
        if(piscando) {
            g.setFill(cor);
            g.setStroke(Color.BLACK);
            g.beginPath();
            g.moveTo(xOlhoEsq-raioOlho, yOlhoEsq);
            g.arc(xOlhoEsq, yOlhoEsq, raioOlho, raioOlho*Math.abs(Math.cos(fasePiscar)), 180, 180);
            g.arc(xOlhoEsq, yOlhoEsq, raioOlho, raioOlho, 0, -180);
            g.closePath();
            g.fill();
            g.stroke();
            
            g.beginPath();
            g.moveTo(xOlhoEsq-raioOlho, yOlhoEsq);
            g.arc(xOlhoEsq, yOlhoEsq, raioOlho, raioOlho*Math.abs(Math.cos(fasePiscar)), 180, -180);
            g.arc(xOlhoEsq, yOlhoEsq, raioOlho, raioOlho, 0, 180);
            g.closePath();
            g.fill();
            g.stroke();
            
            g.beginPath();
            g.moveTo(xOlhoDir-raioOlho, yOlhoDir);
            g.arc(xOlhoDir, yOlhoDir, raioOlho, raioOlho*Math.abs(Math.cos(fasePiscar)), 180, 180);
            g.arc(xOlhoDir, yOlhoDir, raioOlho, raioOlho, 0, -180);
            g.closePath();
            g.fill();
            g.stroke();
            
            g.beginPath();
            g.moveTo(xOlhoDir-raioOlho, yOlhoDir);
            g.arc(xOlhoDir, yOlhoDir, raioOlho, raioOlho*Math.abs(Math.cos(fasePiscar)), 180, -180);
            g.arc(xOlhoDir, yOlhoDir, raioOlho, raioOlho, 0, 180);
            g.closePath();
            g.fill();
            g.stroke();
        }
    }
    
    public void drawTatoo(GraphicsContext g, float xCenterCabeça, float yCenterCabeça) {
        Font font = Font.font("Cambria", FontPosture.ITALIC, raio/3);
        g.setFont(font);
        Text text = new Text(name);
        text.setFont(font);
        g.strokeText(name, xCenterCabeça-text.getLayoutBounds().getWidth()/2, yCenterCabeça+raio/2);
    }
    
    public void drawHitBox(GraphicsContext g) {
        float x = getX();
        float y = getY();
        g.setStroke(Color.BLACK);
        g.strokeRect(x-raio, y-raio, 2*raio, 2*raio);
    }
    
    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }
    
    @Override
    public void draw(GraphicsContext g) {
        float x = getRealX();
        float y = getRealY();
        
        float dxCenter = 0.0f;
        float dyCenter = 0.0f;
        
        float amplitude = raio/20;
        Color backCor = cor;
        if((getTabuleiro() == null) || !getTabuleiro().getJogador().isPoder()) {
            if (direcao == DIREITA || direcao == ESQUERDA) {
                dyCenter += amplitude * Math.sin(fase);
            } else if (direcao == CIMA || direcao == BAIXO) {
                dxCenter += amplitude * Math.sin(fase);
            }
        } else {
            cor = Color.BLUE;
        }
        
        float xCenterCabeça = x+dxCenter;
        float yCenterCabeça = y+dyCenter-raio/15;
        
        if(!isVivo()) {
            g.setGlobalAlpha(0.3f);
            cor = Color.WHITE;
        }
        drawCorpo(g, xCenterCabeça, yCenterCabeça);
        if(!isVivo()) g.setGlobalAlpha(1.0f);
        drawOlhos(g, xCenterCabeça, yCenterCabeça);
        if(!isVivo()) g.setGlobalAlpha(0.3f);
        drawTatoo(g, xCenterCabeça, yCenterCabeça);

        cor = backCor;
    }

    @Override
    public void refresh(float raio) {
        this.raio = 0.8f*raio;
    }
    
    @Override
    public Rectangle2D getBox(float raio) {
        float nraio = (float)(0.4*raio);
        Rectangle2D rec = new Rectangle2D(getRealX()-nraio, getRealY()-nraio, 2*nraio, 2*nraio);
        return rec;
    }
    
}
