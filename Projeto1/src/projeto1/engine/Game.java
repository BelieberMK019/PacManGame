/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import java.util.Scanner;
import projeto1.engine.niveis.Nivel;
import projeto1.engine.niveis.Nivel1;
import projeto1.engine.niveis.Nivel2;
import projeto1.engine.niveis.Nivel3;
import projeto1.engine.niveis.NivelN;
import projeto1.entidades.Player;

/**
 *
 * @author Vitor Rinaldini
 */
public class Game {
    /**Classe responsável pelo gerenciamento do game: Instância do jogador, uma nova fase, etc.
     * @author Vitor Rinaldini
     */
    
    private Tabuleiro tabuleiro;
    private final Scanner scan;
    private final Player player;
    
    /**
     * Construtor vazio. Detalhe, o nome do jogador por enquanto está sendo atribuido no momento de compilação.
     */
    public Game() {
        player = new Player("Beta-Tester");
        scan = new Scanner(System.in);
    }
    
    /**
     * Método para carregar uma fase;
     * @param n Nivel a ser carregado;
     */
    private void loadFase(int n) {
        Nivel nivel;
        if(n == 1) nivel = new Nivel1();
        else if(n == 2) nivel = new Nivel2();
        else if(n == 3) nivel = new Nivel3();
        else if(n > 3) nivel = new NivelN(n);
        else return;
        tabuleiro = new Tabuleiro(player, nivel);
        tabuleiro.update();
    }
    
    /**
     * Comandos para o jogador se mover.
     */
    public void exibirComandos() {
        System.out.print("w -> mover para cima; ");
        System.out.println("d -> mover para direita;");
        System.out.print("a -> mover para esquerda; ");
        System.out.println("s -> mover para baixo;");
        System.out.print("q -> fechar e sair; ");
    }
    
    /**
     * Lê uma entrada do jogador e executa uma ação.
     */
    public void capturarEExecutar() {
        System.out.print("Insira: ");
        char c =  scan.next().charAt(0);
        if(c == 'w') moverCima();
        else if(c == 'd') moverDireita();
        else if(c == 'a') moverEsquerda();
        else if(c == 's') moverBaixo();
        else if(c == '*') tabuleiro.getGrafo().print(); //debug
        else if(c == '/') tabuleiro.printFantasmas(); //debug
        else if(c == 'q') System.exit(0);
    }
    
    public void moverCima() {
        tabuleiro.getJogador().moverX(-1);
    }
    
    public void moverDireita() {
        tabuleiro.getJogador().moverY(+1);
    }
    
    public void moverEsquerda() {
        tabuleiro.getJogador().moverY(-1);
    }
    
    public void moverBaixo() {
        tabuleiro.getJogador().moverX(+1);
    }
    
    public void imprimirPontuacao() {
        System.out.println("Pontuação parcial: "+ player.getScore());
        System.out.println("Pontuação Acumulada: "+ player.getScoreAcumulado());
    }
    
    public void imprimirVidas() {
        System.out.println("Vidas Restantes: "+ player.getVidas());
    }
    
    /**
     * Executa o loop princial de uma fase.
     */
    public void loopPrincipal() {
        while(tabuleiro.isPlaying()) {
            System.out.println("");
            tabuleiro.printTabuleiro();
            imprimirPontuacao();
            imprimirVidas();
            exibirComandos();
            capturarEExecutar();
            if (tabuleiro.getJogador().isPoder()) {
                tabuleiro.getJogador().usarPoder();
            }
            tabuleiro.detectarColisao();
            tabuleiro.update();
        }
    }
    
    /**
     * Confere se ganhou aquela fase.
     * @return TRUE, se ganhou; FALSE, se perdeu.
     */
    private boolean ganhou() {
        float pontuacaoTotal =  player.getScore()+player.getScoreAcumulado();
        System.out.println("Sua pontuação atual é: "+ pontuacaoTotal);
        if(!tabuleiro.getJogador().isVivo()) {
            telaGameOver();
            return false;
        } else {
            telaWin();
            return  true;
        }
    }
    
    /**
     * Exibe a tela de game over pra console.
     */
    public void telaGameOver() {
        System.out.println("Venceu!!");
        System.out.println("┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n"
                + "███▀▀▀██┼███▀▀▀███┼███▀█▄█▀███┼██▀▀▀\n"
                + "██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼█┼┼┼██┼██┼┼┼\n"
                + "██┼┼┼▄▄▄┼██▄▄▄▄▄██┼██┼┼┼▀┼┼┼██┼██▀▀▀\n"
                + "██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██┼┼┼\n"
                + "███▄▄▄██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██▄▄▄\n"
                + "┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n"
                + "███▀▀▀███┼▀███┼┼██▀┼██▀▀▀┼██▀▀▀▀██▄┼\n"
                + "██┼┼┼┼┼██┼┼┼██┼┼██┼┼██┼┼┼┼██┼┼┼┼┼██┼\n"
                + "██┼┼┼┼┼██┼┼┼██┼┼██┼┼██▀▀▀┼██▄▄▄▄▄▀▀┼\n"
                + "██┼┼┼┼┼██┼┼┼██┼┼█▀┼┼██┼┼┼┼██┼┼┼┼┼██┼\n"
                + "███▄▄▄███┼┼┼─▀█▀┼┼─┼██▄▄▄┼██┼┼┼┼┼██▄\n");
    }
    
    /**
     * Exibe a tela de vitoria para console.
     */
    public void telaWin() {
        System.out.println("Perdeu :-(");
        System.out.println("██╗░░░██╗░█████╗░░█████╗░███████╗  ░██████╗░░█████╗░███╗░░██╗██╗░░██╗░█████╗░██╗░░░██╗██╗\n"
                + "██║░░░██║██╔══██╗██╔══██╗██╔════╝  ██╔════╝░██╔══██╗████╗░██║██║░░██║██╔══██╗██║░░░██║██║\n"
                + "╚██╗░██╔╝██║░░██║██║░░╚═╝█████╗░░  ██║░░██╗░███████║██╔██╗██║███████║██║░░██║██║░░░██║██║\n"
                + "░╚████╔╝░██║░░██║██║░░██╗██╔══╝░░  ██║░░╚██╗██╔══██║██║╚████║██╔══██║██║░░██║██║░░░██║╚═╝\n"
                + "░░╚██╔╝░░╚█████╔╝╚█████╔╝███████╗  ╚██████╔╝██║░░██║██║░╚███║██║░░██║╚█████╔╝╚██████╔╝██╗\n"
                + "░░░╚═╝░░░░╚════╝░░╚════╝░╚══════╝  ░╚═════╝░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝░░╚═╝░╚════╝░░╚═════╝░╚═╝");
    }
    
    /**
     * Loop principal do jogo.
     */
    public void jogar() {
        telaInicial();
        int nivel = 1;
        while(true) {
            System.out.println("Carregando nivel "+ nivel+"...");
            loadFase(nivel);
            System.out.println("Vamos começar :-D");
            loopPrincipal();
            if(!ganhou()) return;
            System.out.println("Parabens por completar o nivel "+ nivel+"!");
            nivel++;
        }
    }
    
    /**
     * Método para realizar o teste da entrega 1.
     */
    public void teste1() {
        loadFase(1);
        char c = 'w';
        while(c != 'q') {
            tabuleiro.resetPositionAndCaminhoToTeste();
            tabuleiro.printTeste1();
            System.out.println("Legenda: P - Player; B - Fantasma.");
            System.out.println("q -> Encerra a aplicação;");
            System.out.println("Qualquer outra letra -> Nova execução!");
            System.out.print("Insira ");
            c = scan.next().charAt(0);
            System.out.println("\n");
        }
    }
    
    /**
     * Tela de boas-Vindas + legenda do jogo.
     */
    public void telaInicial() {
        System.out.println("Olá! Esta versão do game não apresenta interface gráfica, mas ela foi adaptada e poderá ser jogada via console.");
        System.out.println("Pegue todos os Pac-Dots, avance aos próximos níveis e conquiste a maior pontuação.");
        System.out.println("CUIDADO com os fantasmas, alguns são espertos e irão até o jogador!");
        System.out.println("Diverta-se! :-D");
        System.out.println("");
        System.out.println("\tLegenda:");
        System.out.println("A -> Fantasma Fugindo;");
        System.out.println("I -> Fantasma aleatório Inky;");
        System.out.println("C -> Fantasma aleatório Clyde;");
        System.out.println("R -> Fantasma esperto Pinky;");
        System.out.println("B -> Fantasma esperto Blinky. CUIDADO, pois ele pode ficar mais rápido;");
        System.out.println("o -> ??????? Mistério... Não se preocupe com isso;");
        System.out.println("8 -> Cereja;");
        System.out.println("6 -> Morango;");
        System.out.println("Q -> Laranja;");
        System.out.println("P -> Pac-Man/Player;");
        System.out.println(". -> Pac-Dots;");
        System.out.println("+ -> Pílula;");
        System.out.println("# -> Parede;");
        System.out.println("= -> Porteira dos fantasmas;");
        System.out.println("");
        System.out.println("Insira algo para continuar...");
        scan.next();
        
    }
}
