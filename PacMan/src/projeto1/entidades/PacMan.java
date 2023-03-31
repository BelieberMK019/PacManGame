/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.niveis.Nivel;
import projeto1.entidades.fantasmas.Fantasma;

/**
 *
 * @author Vitor Rinaldini
 */
public class PacMan extends Entidade{

    /**
     * Classe responsável pela entidade em que o jogador irá controlar = PacMan.
     * @author Vitor Rinaldini.
     */
    private final Player player;
    
    private final char charImag = 'P';
    private float timeRestantesComPoder;
    private int fantasmasMortosComPoder;
    private InputKeyController teclado;
    
    /**
     * Construtor.
     * @param x
     * @param y
     * @param tabuleiro
     * @param player 
     */
    public PacMan(float x, float y, Tabuleiro tabuleiro, Player player, InputKeyController teclado) {
        super(x, y, tabuleiro);
        this.teclado = teclado;
        this.player = player;
        timeRestantesComPoder = 0;
        fantasmasMortosComPoder = 0;
    }
    
    /**
     * Pegar poder da pilula.
     * @param nivel 
     */
    public void pegarPoder(Nivel nivel) {
        timeRestantesComPoder = nivel.getTIME_WITH_PODER();
        fantasmasMortosComPoder = 0;
    }
    
    /**
     * Usar poder da pilula.
     */
    public void usarPoder(float deltaTime) {
        timeRestantesComPoder -= deltaTime;
        if(timeRestantesComPoder <=0) {
            getTabuleiro().resetFantasmas();
            fantasmasMortosComPoder = 0;
        }
    }
    
    /**
     * Confere se ele está com a pilula ativada.
     * @return 
     */
    public boolean isPoder() {
        return timeRestantesComPoder > 0;
    }
    
    /**
     * Retorna as vezes restantes com a pilula.
     * @return int
     */
    public float getTimeRestantesComPoder() {
        return timeRestantesComPoder;
    }
    
    /**
     * Adiciona pontuação.
     * @param pontos 
     */
    public void addPontos(float pontos) {
        player.addPontos(pontos);
    }
    
    /**
     * Come um fantasma.
     */
    public void comerFantasma() {
        addPontos((float) (Fantasma.PONTOS_FANTASMA*Math.pow(2, fantasmasMortosComPoder)));
        fantasmasMortosComPoder++;
    }
    
    /**
     * Retorna a pontuação atual.
     * @return 
     */
    public float getPontuacao() {
        return player.getScore();
    }
    
    /**
     * Retorna a quantidade de vidas restantes.
     * @return 
     */
    public int getVidas() {
        return player.getVidas();
    }
    
    /**
     * Retorna a imagem do PacMan pro terminal.
     * @return 
     */
    @Override
    public char getCharImag() {
        return charImag;
    }

    /**
     * Mata o PacMan.
     */
    @Override
    public void morrer() {
        super.morrer(); //To change body of generated methods, choose Tools | Templates.
        player.perderVida();
    }

    @Override
    public void moverX(float dx) {
        super.moverX(dx);
    }

    @Override
    public void moverY(float dy) {
        super.moverY(dy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    private Color cor = Color.YELLOW;
    private float raio = 1f;
    private float angleMax = 50; //graus
    private float velociadeAngular = 180;
    private float angle = 0;
    
    public final int DIREITA = 0;
    public final int ESQUERDA = 1;
    public final int CIMA = 2;
    public final int BAIXO = 3;
    private int direcao = DIREITA;
    
    public boolean movendo = true;
    
    @Override
    public void update(double deltaTime) {
        
        float dist = (float) (deltaTime*getVelocidade()); 
        if (teclado != null) {
            movendo = true;
            if (teclado.d || teclado.arrowRight) {
                moverX(+dist);
                direcao = DIREITA;
            } else if (teclado.a || teclado.arrowLeft) {
                moverX(-dist);
                direcao = ESQUERDA;
            } else if (teclado.s || teclado.arrowDown) {
                moverY(+dist);
                direcao = BAIXO;
            } else if (teclado.w || teclado.arrowUp) {
                moverY(-dist);
                direcao = CIMA;
            } else {
                movendo = false;
            }
        }
        
        
        if(movendo) {
            float deltaAngle = (float) (velociadeAngular * deltaTime);

            while (deltaAngle != 0) {
                angle += deltaAngle;

                if (angle < 0) {
                    deltaAngle = -angle;
                    angle = 0;
                    velociadeAngular *= -1;
                } else if (angle > angleMax) {
                    deltaAngle = -(angle - angleMax);
                    angle = angleMax;
                    velociadeAngular *= -1;
                } else {
                    deltaAngle = 0;
                }

            }
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        float angleInicial = 0;
        float x = getX();
        float y = getY();
        
        if(direcao == DIREITA) {
            angleInicial = angle/2;
        } else if(direcao == ESQUERDA) {
            angleInicial = 180+angle/2;
        } else if(direcao == CIMA) {
            angleInicial = 90+angle/2;
        } else if(direcao == BAIXO){
            angleInicial = 270+angle/2;
        }
        
        g.setFill(cor);
        g.fillArc(x-raio, y-raio, 2*raio, 2*raio, angleInicial, 360-angle, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.strokeArc(x-raio, y-raio, 2*raio, 2*raio, angleInicial, 360-angle, ArcType.ROUND);
        
        float distOlho = 0.7f*raio;
        float raioOlho = 0.2f*raio;
        
        g.setFill(Color.BLACK);
        if(direcao == DIREITA){ 
            g.fillOval(x+distOlho*Math.cos(45*Math.PI/180)-raioOlho, y-distOlho*Math.sin(45*Math.PI/180)-raioOlho, raioOlho, raioOlho);
        } else if(direcao == ESQUERDA) {
            g.fillOval(x-distOlho*Math.cos(45*Math.PI/180)-raioOlho, y-distOlho*Math.sin(45*Math.PI/180)-raioOlho, raioOlho, raioOlho);
        } else if(direcao == CIMA) {
            g.fillOval(x-distOlho*Math.cos(45*Math.PI/180)-raioOlho, y-distOlho*Math.sin(45*Math.PI/180)-raioOlho, raioOlho, raioOlho);
        } else if(direcao == BAIXO) {
            g.fillOval(x-distOlho*Math.cos(45*Math.PI/180)-raioOlho, y+distOlho*Math.sin(45*Math.PI/180)-raioOlho, raioOlho, raioOlho);
        }
    }

    @Override
    public void refresh(float raio) {
        this.raio = raio;
    }

    @Override
    public Rectangle2D getBox(float raio) {
        float nraio = 0.7f*raio;
        Rectangle2D rec = new Rectangle2D(getX()-nraio, getY()-nraio, 2*nraio, 2*nraio);
        return rec;
    }
    
    
}
