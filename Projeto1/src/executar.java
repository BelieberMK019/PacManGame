
import java.util.Scanner;
import projeto1.engine.Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vitor Rinaldini
 */
public class executar {
    
    /** Inicio de Programa.
     *  O usuário terá duas opoções: a primeira (A) é uma amostra visual do caminho que será percorrido pela IA até o jogador, sendo as posições atribuidas aleatoriamente
     * A segunda (B) é uma amostra do jogo em si. Apesar de não contar com visual gráfico, o jogo pode ser jogado via console.
     * Versão Beta. Pode conter bugs
     * @param args 
     */
    public static void main(String[] args) {
        Game game = new Game();
        Scanner s = new Scanner(System.in);
        System.out.println("Apesar dessa primeira entrega não conter interface gráfica,\nvocê pode jogar uma amostra (opção A) ou visualizar o caminho que será percorrido pelo fantama (Opção B)");
        System.out.print("Insira a opção desejada: ");
        char op = s.next().charAt(0);
        System.out.println("\n");
        if(op == 'A') {
            game.jogar();
        } else if(op == 'B') {
            game.teste1();
        } else {
            System.out.println("Opção inválida!");
        }
    }
}
