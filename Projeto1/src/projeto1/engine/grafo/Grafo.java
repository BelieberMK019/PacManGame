/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.grafo;

import projeto1.engine.uteis.Ponto2f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import projeto1.engine.Mapa;
import projeto1.entidades.Entidade;

/**
 *
 * @author Vitor Rinaldini
 */
public class Grafo {
    /**
     * Classe responsável pela implementação do grafo e dos métodos de IA
     * @author Vitor Rinaldini
     */
    private float xMax;
    private float yMax;
    private final ArrayList<Node> nodes;
    private final Mapa mapa;
    
    /**
     * Construtor
     * @param xMax
     * @param yMax
     * @param mapa 
     */
    public Grafo(float xMax, float yMax, Mapa mapa) {
        nodes = new ArrayList();
        this.xMax = xMax;
        this.yMax = yMax;
        this.mapa = mapa;
    }
    
    /**
     * Retorna a quantidade de nodes que há no grafo.
     * @return 
     */
    public int quantidadeNodes() {
        return nodes.size();
    }
    
    /**
     * Retorna um node do grafo.
     * @param i
     * @return 
     */
    public Node getNode(int i) {
        if(i < 0 || i >= nodes.size()) {
            return null;
        } else {
            return nodes.get(i);
        }
    }
    
    /**
     * Cria um node dado as coordenadas e o adiciona no grafo.
     * @param x
     * @param y
     * @return 
     */
    public Node adicionar(float x, float y) {
        Node node = new Node(x, y);
        int i = buscar(node);
        if(i < 0) {
            nodes.add(node);
            return node;
        } else {
            return nodes.get(i);
        }
    }
    
    /**
     * Adiciona uma entidade ao grafo, sem conectá-la.
     * @param entidade
     * @return 
     */
    public int adicionarEntidade(Entidade entidade) {
        nodes.add(entidade);
        return nodes.size()-1;
    }
    
    /**
     * Adiciona um node ao Grafo
     * @param node
     * @return 
     */
    public int adicionar(Node node) {
        int i = buscar(node);
        if(i < 0) {
            nodes.add(node);
            return nodes.size()-1;
        } else {
            return i;
        }
    }
    
    /**
     * confere se o grafo contem tal node na forma (x, y).
     * @param x
     * @param y
     * @return 
     */
    public boolean contem(float x, float y) {
        Node node = new Node(x, y);
        for(Node atual: nodes) {
            if(atual.isIgual(node)) return true;
        }
        return false;
    }
    
    /**
     * retorna um node que tenha aquelas coordenadas.
     * @param x
     * @param y
     * @return 
     */
    public int buscar(float x, float y) {
        return buscar(new Node(x, y));
    }
    
    /**
     * confere se há um node no grafico. 
     * @param node
     * @return 
     */
    public int buscar(Node node) {
        for(int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).isIgual(node)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * remove um node do grafo
     * @param node 
     */
    public void remover(Node node) {
        for(int i = 0; i < node.getQuantidadeConexoes(); ) {
            node.desconectar(node.getNode(i));
        }
        nodes.remove(node);
        
    }
    /**
     * Desconecta a entidade do grafo.
     * @param entidade 
     */
    public void removerEntidade(Entidade entidade) {
        if(entidade.getQuantidadeConexoes() == 2) {
            entidade.getNode(0).conectar(entidade.getNode(1));
        }
        
        for(int i = 0; i < entidade.getQuantidadeConexoes(); ) {
            entidade.desconectar(entidade.getNode(i));
        }
        nodes.remove(entidade);
        
    }
    
    /**
     * Conecta dois Nodes
     * @param node1
     * @param node2 
     */
    public void conectar(Node node1, Node node2) {
        if(node1 == null || node2 == null) return;
        node1.conectar(node2);
    }
    
    /**
     * retorna os Nodes mais proximos à um ponto dado no formato (x,y).
     * @param x
     * @param y
     * @return 
     */
    public ArrayList<Node> getNodesProximos(float x, float y) {
        Ponto2f meuPonto = new Ponto2f(x, y);
        ArrayList<NodeComparavel> comparaveis = new ArrayList();
        for(Node node: nodes) {
            if(node != meuPonto) {
                NodeComparavel nc1 = new NodeComparavel(node, node.distancia(meuPonto));
                comparaveis.add(nc1);
            }
        }
        
        Collections.sort(comparaveis);
        
        ArrayList<Node> nodesOrdenados = new ArrayList();
        for(NodeComparavel node: comparaveis) {
            nodesOrdenados.add(node.getNode());
        }
        
        return nodesOrdenados;
    }
    
    /**
     * retorna os Nodes mais proximos à um ponto dado
     * @param meuPonto
     * @return 
     */
    public ArrayList<Node> getNodesProximos(Node meuPonto) {
        ArrayList<NodeComparavel> comparaveis = new ArrayList();
        for(Node node: nodes) {
            if(node != meuPonto) {
                NodeComparavel nc1 = new NodeComparavel(node, node.distancia(meuPonto));
                comparaveis.add(nc1);
            }
        }
        
        Collections.sort(comparaveis);
        
        ArrayList<Node> nodesOrdenados = new ArrayList();
        for(NodeComparavel node: comparaveis) {
            nodesOrdenados.add(node.getNode());
        }
        
        return nodesOrdenados;
    }
    
    /**
     * Mesma ideia do GetNode
     * @param i
     * @return 
     */
    private Node convert(int i) {
        if(i < 0) return null;
        else return nodes.get(i);
    }
    
    /**
     * Se a variavel estiver fora dos limites do jogo, o método colocará ela numa borda.
     * @param var
     * @param max
     * @return 
     */
    private float normalizarVar(float var, float max) {
        if(var < 0) return max-1f;
        else if(var >= max) return 0;
        else return var;
    }
    
    /**
     * conectar Nodes adjacentes ligando as bordas.
     */
    public void conectarNosAdjacentesComBordas(float xMax, float yMax) {
        for(Node node: nodes) {
            Node direita = convert(buscar(node.getX(), normalizarVar(node.getY()+1, yMax)));
            Node esquerda = convert(buscar(node.getX(), normalizarVar(node.getY()-1, yMax)));
            Node cima = convert(buscar(normalizarVar(node.getX()-1, xMax), node.getY()));
            Node baixo = convert(buscar(normalizarVar(node.getX()+1, xMax), node.getY()));
            
            conectar(node, direita);
            conectar(node, esquerda);
            conectar(node, cima);
            conectar(node, baixo);
        }
    }
    
    /**
     * conectar Nodes adjacentes sem ligar as bordas.
     */
    public void conectarNosAdjacentesSemBordas() {
        for(Node node: nodes) {
            Node direita = convert(buscar(node.getX(), node.getY()+1));
            Node esquerda = convert(buscar(node.getX(), node.getY()-1));
            Node cima = convert(buscar(node.getX()-1, node.getY()));
            Node baixo = convert(buscar(node.getX()+1, node.getY()));
            
            conectar(node, direita);
            conectar(node, esquerda);
            conectar(node, cima);
            conectar(node, baixo);
        }
    }
    
    /**
     * Retornar nodes com o mesmo X
     * @param node
     * @return 
     */
    private ArrayList<Node> getNodesWithX(Node node) {
        ArrayList<Node> result = new ArrayList();
        for(Node atual: nodes) {
            if(atual != node && atual.getX() == node.getX()) {
                result.add(atual);
            }
        }
        return result;
    }
    
    /**
     * Retornas nodes com o mesmo Y
     * @param node
     * @return 
     */
    private ArrayList<Node> getNodesWithY(Node node) {
        ArrayList<Node> result = new ArrayList();
        for(Node atual: nodes) {
            if(atual != node && atual.getY() == node.getY()) {
                result.add(atual);
            }
        }
        return result;
    }
    
    /**
     * conectar nodes Spawn fantasmas e mapa do jogo.
     * @param porteiras 
     */
    public void abrirPorteiras(ArrayList<Ponto2f> porteiras) {
        for(Ponto2f porteira: porteiras) {
            Node node1, node2;
            node1 = getNode(buscar(porteira.getX()-1f, porteira.getY()));
            node2 = getNode(buscar(porteira.getX()+1f, porteira.getY()));
            conectar(node1, node2);
        }
    }
    
    /**
     * desconectar nodes Spawn fantasmas e mapa do jogo.
     * @param porteiras 
     */
    public void fecharPorteiras(ArrayList<Ponto2f> porteiras) {
        for(Ponto2f porteira: porteiras) {
            Node node1, node2;
            node1 = getNode(buscar(porteira.getX()-1f, porteira.getY()));
            node2 = getNode(buscar(porteira.getX()+1f, porteira.getY()));
            node1.desconectar(node2);
        }
    }
    
    /**
     * Retornar Nodes na borda.
     * @param node
     * @return 
     */
    private ArrayList<Node> getNodesInTheBounds(Node node) {
        ArrayList<Node> result = new ArrayList();
        ArrayList<Node> maisProximos = getNodesProximos(node);     
        if(node.isIgual(maisProximos.get(0))) {
                result.add(maisProximos.get(0));
                return result;
        }
        
        ArrayList<Node> nodesX = getNodesWithX(node);
        Collections.sort(nodesX, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                float y1 = o1.getY();
                float y2 = o2.getY();
                if(y1 > y2) return 1;
                else if(y1 < y2) return -1;
                else return 0;
            }  
        });
        
        for(int i = 0; i < nodesX.size(); i++) {
            for(int j = 0; j < nodesX.size();j++) {
                if(i != j) {
                    Node o1 = nodesX.get(i);
                    Node o2 = nodesX.get(j);
                    if (!o1.isBorda(o2, xMax, yMax) && o1.isConected(o2) && node.getY() > o1.getY() && node.getY() < o2.getY()) {
                        o1.desconectar(o2);/////
                        result.add(o1);
                        result.add(o2);
                        return result;
                    }
                }
            }
        }
        ArrayList<Node> nodesY = getNodesWithY(node);
        Collections.sort(nodesY, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                float x1 = o1.getX();
                float x2 = o2.getX();
                if(x1 > x2) return 1;
                else if(x1 < x2) return -1;
                else return 0;
            }  
        });
        
        for(int i = 0; i < nodesY.size(); i++) {
            for(int j = 0; j < nodesY.size();j++) {
                if(i != j) {
                    Node o1 = nodesY.get(i);
                    Node o2 = nodesY.get(j);
                    if (!o1.isBorda(o2, xMax, yMax) &&o1.isConected(o2) && node.getX() > o1.getX() && node.getX() < o2.getX()) {
                        o1.desconectar(o2);/////
                        result.add(o1);
                        result.add(o2);
                        return result;
                    }
                }
            }
        }
        
        return result;
    } 
    
    /**
     * Nodes em linha reta serão ligados e nodes intermediários seráo apagados.
     * Método de otimização.
     */
    public void normalizar() {
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(!node.isBorda(xMax, yMax) && node.getQuantidadeConexoes() == 2 && !mapa.isSpawnMonstros((int)node.getX(), (int)node.getY()) && !mapa.isPerimetroPorteira((int)node.getX(), (int)node.getY())) {
                Node filho1 = node.getNode(0);
                Node filho2 = node.getNode(1);
                if(node.isAlinhado(filho1, filho2)){
                    filho1.conectar(filho2);
                    remover(node);
                    i--;
                }
            }
        }
    }
    
    /**
     * Implementação da busca Aleatoria.
     * @param inicio
     * @return 
     */
    public Caminho buscaAleatoria(Node inicio) {
        Caminho caminho = new Caminho(xMax, yMax);
        caminho.add(inicio);
        
        if(inicio.getQuantidadeConexoes() != 0) {
            int numberAleatorio = (int) (Math.random() * (inicio.getQuantidadeConexoes()-1));
            caminho.add(inicio.getNode(numberAleatorio));
        }
        
        if(caminho.get(0).isIgual(caminho.get(1))) {
            Node atual = caminho.getLastNode();
            if(atual.getQuantidadeConexoes() != 0) {
                int numberAleatorio = (int) (Math.random() * (atual.getQuantidadeConexoes()-1));
                caminho.add(atual.getNode(numberAleatorio));
            }
        }
        
        return caminho;
    }
    
    /**
     * Retorna um pontoAleatorioDoGrafo
     * @return 
     */
    public Ponto2f pontoAleatorioNoGrafo() {
        int numeroAleatorio =(int) Math.round(Math.random()*nodes.size());
        Node nodeAleatorio1 = nodes.get(numeroAleatorio);
        int numeroAleatorio2 = (int) Math.round(Math.random()*(nodeAleatorio1.getQuantidadeConexoes())-1);
        Node nodeAleatorio2 = nodeAleatorio1.getNode(numeroAleatorio2);
        
        float modulo = (float) Math.random();
        return new Ponto2f((int)(nodeAleatorio1.getX()+modulo*(nodeAleatorio2.getX()-nodeAleatorio1.getX())),
                            (int)(nodeAleatorio1.getY()+modulo*(nodeAleatorio2.getY()-nodeAleatorio1.getY())));
    }
    
    /**
     * Implementação da busca largura Distancia
     * @param inicio
     * @param fim
     * @return 
     */
    public Caminho buscaLarguraDist(Node inicio, Node fim) {
        ArrayList<Caminho> caminhos = new ArrayList();
        
        Caminho partida = new Caminho(xMax, yMax);
        partida.add(inicio);
        caminhos.add(partida);
        
        while(caminhos.size() > 0) {
            //Caso final da busca => ACHOU
            if(caminhos.get(0).getLastNode().isIgual(fim)) {
                return caminhos.get(0);
            }
            
            //Adicionando as conexões do nó testado à lista de possíveis testes
            for(Node node: caminhos.get(0).getLastNode().getNodes()) {
                boolean jaTem = caminhos.get(0).contains(node);
                if(!jaTem) {
                    Caminho novo = caminhos.get(0).getCopiaCaminho();
                    novo.add(node);
                    caminhos.add(novo);
                }
            }
            
            //removendo o caminho e ordenando por menor estimativa
            caminhos.remove(0);
            Collections.sort(caminhos,new Comparator<Caminho>() {
                @Override
                public int compare(Caminho o1, Caminho o2) {
                    if(o1.getDistanciaPercorrida() > o2.getDistanciaPercorrida()) return 1;
                    else if(o1.getDistanciaPercorrida() < o2.getDistanciaPercorrida()) return -1;
                    else return 0;
                }

            });
            
        }
        
        return null;
    }
    
    /**
     * Implementação da busca A*
     * @param inicio
     * @param fim
     * @return 
     */
    public Caminho buscaAEstrela(Node inicio, Node fim) {
        ArrayList<CaminhoEstimado> caminhos = new ArrayList();
        
        CaminhoEstimado partida = new CaminhoEstimado(fim, xMax, yMax);
        partida.add(inicio);
        caminhos.add(partida);
        
        while(caminhos.size() > 0) {
            //Caso final da busca => ACHOU
            if(caminhos.get(0).getLastNode().isIgual(fim)) {
                return caminhos.get(0);
            }
            
            //Adicionando as conexões do nó testado à lista de possíveis testes
            for(Node node: caminhos.get(0).getLastNode().getNodes()) {
                boolean jaTem = caminhos.get(0).contains(node);
                if(!jaTem) {
                    CaminhoEstimado novo = caminhos.get(0).getCopia();
                    novo.add(node);
                    caminhos.add(novo);
                }
            }
            
            //removendo o caminho e ordenando por menor estimativa
            caminhos.remove(0);
            Collections.sort(caminhos);
            
        }
        
        return null;
    }
    
    /**
     * Ligar node da entidade ao grafo
     * @param entidade 
     */
    public void updateEntidade(Entidade entidade) {
        if(entidade == null) return;
        
        remover(entidade);
        adicionarEntidade(entidade);
        ArrayList<Node> bounds = getNodesInTheBounds(entidade);;
        if(bounds.size() > 0){
            conectar(entidade ,bounds.get(0));
            if(bounds.size() > 1) {
                conectar(entidade, bounds.get(1));
            }
        }
        
    }
    
    /**
     * Realizar Busca largura distancia até um ponto.
     * @param inicio
     * @param fim
     * @return caminho
     */
    public Caminho fazerBuscaLargDistToPonto(Entidade inicio, Ponto2f ponto) {
        adicionarEntidade(inicio);
        updateEntidade(inicio);
        Node fim = getNode(buscar(ponto.getX(), ponto.getY())); 
        Caminho caminho = buscaLarguraDist(inicio, fim);
        if(caminho!= null) caminho.removerDuplicatasSeguidas();
        removerEntidade(inicio);
        return caminho;
    }
    
    /**
     * Realizar Busca Largura Distancia.
     * @param inicio
     * @param fim
     * @return caminho
     */
    public Caminho fazerBuscaLargDist(Entidade inicio, Entidade fim) {
        adicionarEntidade(fim);
        updateEntidade(fim);
        adicionarEntidade(inicio);
        updateEntidade(inicio);
        Caminho caminho = buscaLarguraDist(inicio, fim);
        if(caminho!= null) caminho.removerDuplicatasSeguidas();
        removerEntidade(inicio);
        removerEntidade(fim);
        return caminho;
    }
    
    /**
     * Realizar Busca heuristica A*.
     * @param inicio
     * @param fim
     * @return caminho
     */
    public Caminho fazerBuscaAEstrela(Entidade inicio, Entidade fim) {
        adicionarEntidade(fim);
        updateEntidade(fim);
        adicionarEntidade(inicio);
        updateEntidade(inicio);
        Caminho caminho = buscaAEstrela(inicio, fim);
        if(caminho!= null) caminho.removerDuplicatasSeguidas();
        removerEntidade(inicio);
        removerEntidade(fim);
        return caminho;
    }
    
    /**
     * Realizar Busca Aleatoria.
     * @param inicio
     * @return caminho
     */
    public Caminho fazerBuscaAleatoria(Entidade inicio) {
        adicionarEntidade(inicio);
        updateEntidade(inicio);
        Caminho caminho = buscaAleatoria(inicio);
        caminho.removerDuplicatasSeguidas();
        removerEntidade(inicio);
        return caminho;
    }
    /**
     * Método para Debug
     * Imprimir grafo.
     */
    public void print() {
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            System.out.print("i: " +i+ " ("+ node.getX()+"; "+ node.getY()+")");
            System.out.print(" F: ");
            for(Node filho: node.getNodes()) System.out.print("("+ filho.getX()+"; "+ filho.getY()+")");
            System.out.println("");
        }
        System.out.println("Quantidade de nodes: "+ nodes.size());
    }
}
