
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projeto1.engine.InputKeyController;
import projeto1.graficos.MainFrameController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vitor Rinaldini
 */
public class executar extends Application{
    
    /** Inicio de Programa.
     *  O usuário terá duas opoções: a primeira (A) é uma amostra visual do caminho que será percorrido pela IA até o jogador, sendo as posições atribuidas aleatoriamente
     * A segunda (B) é uma amostra do jogo em si. Apesar de não contar com visual gráfico, o jogo pode ser jogado via console.
     * Versão Beta. Pode conter bugs
     * @param args 
     */
    public static void main(String[] args) {
        
        launch(args);
        
        /*Game game = new Game();
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
        }*/
    }

    @Override
    public void start(Stage stage) throws Exception {
        InputKeyController teclado = new InputKeyController();
        MainFrameController controladora = new MainFrameController(teclado);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        loader.setController(controladora);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(teclado);
        scene.setOnKeyReleased(teclado);
        
        stage.setScene(scene);
        stage.setTitle("Jogo do Pac - Man");
        stage.setResizable(false);
        stage.show();
    }
}
