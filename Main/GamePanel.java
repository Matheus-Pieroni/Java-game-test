package Main;

//Esse primeiro import é justamente nossa classe "Player", e seus métodos.
import Entity.Player;
import Tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    /*
    OI DENOVO!! :3
    
    Aqui tem MUITA COISA PRA COMENTAR! (Entendeu a piada? "Comentar")
    ENFIM ^_^
    
    Aqui teremos comentarios separados explicando melhor tudo o que ta acontecendo, e acredite
    TEM COISA PRA CARAMBA.
    */
    
    /*
    Aqui temos a definição de algumas variaveis que serão utilizadas depois em varias partes diferentes do código,
    já que muito de toda essa "configuração" pode ser reutilizada (ou seja, não precisa ser reescrita! Yay!)
    */
    final int originalTileSize = 16; //16x16 tiles (character, map, etc)
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    //AQUI OCORRE O INSTÂNCIAMENTO DAS CLASSES CRIADAS!!!
    //fps (not first person shooter)
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    
    /*
    Montando o CONSTRUTOR DO GAMEPANEL, ou seja, da nossa classe.
    Essa é a configuração da nossa área REAL de jogo, a configuração feita antes
    era meio que placebo.
    */
    public GamePanel() {
        
        //Caracteristicas importantes do nosso JPanel customizado.
        //E realiza o setup de:
        
        /*
        Nosso tamanho REAL da tela em uso. (Sim, o Java tem dessas. Se você colocar um tamanho no JFrame ele não vai contar 
        a barra de cima da tela e mais alguns elementos gráficos, o que fará com que varias coisas acabem "cortadas"
        na tela.
        */
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        //Da uma cor legal pro fundo.
        this.setBackground(Color.BLACK);
        
        //Não faço a menor ideia do porque nós precisamos/usamos um buffer aqui, mas...
        this.setDoubleBuffered(true);
        
        /*
        Adiciona a "leitura do teclado" pelo nosso objeto. (Para a captura de inputs do usuário)
        e passa o argumento "KeyH", que será utilizado pela classe "KeyHandler" para movimentar o personagem em jogo.
        */
        this.addKeyListener(keyH);
        
        //Permite que a "Janela", (no caso, nosso "Canvas", ou JPanel do Toretto) seja focada ao clicar.
        this.setFocusable(true);
    }

    public void startGameThread() {
        
        /*
        Isso aqui é muito doido, e eu to falando sério!
        
        Aqui vem uma parte MUITO INTERESSANTE conhecida como "Threads".
        
        O "Thread", nesse caso aqui abaixo, é o coração do jogo!
        O Framerate, os "Gráphicos" e comandos são todos trabalho do Thread.
        Ele é tipo um processo "aparte da classe main", como um filinho dela, e esses dois rodam SIMULTANEAMENTE.
        (depois da classe main ter "se resolvido" é claro.)
        
        Aqui vem a parte legal, ta vendo isso aqui abaixo?
        Estamos instânciando um processo Thread, DENTRO DO PRÓPRIO OBJETO DO GAMEPANEL!
        
        É uma loucura porque, geralmente você cria uma classe diferente e roda o processo de uma outra.
        Criando uma cadeia maluca, mas aqui não, estamos iniciando o processo "run()" do Thread na própria classe!!!
        Por isso desse "new Thread(this)". Geralmente passamos o nome do nosso objeto com o "implements Runnable".
        
        Como argumento desse Thread, como se estivessemos rodando uma classe diferente de forma normal,
        mas aqui nós damos tipo um "Overload" no método "main" da classe por rodar o método "run()" em seu lugar! Maluquice né!?
        */
        // Criamos a "instância" do Thread em cima do nosso método GamePanel!
        gameThread = new Thread(this);
        //Aqui nós iniciamos o Thread em cima do objeto do GamePanel.
        gameThread.start();
    }

    /*
    E Aqui rodamos continuamente o nosso Thread!
    O que contém várias definições importantes para que um jogo de "qualidade" seja desenvolvido em Java.
    Tipo:
    */
    @Override
    public void run() {

        //Aqui definimos LITERALMENTE o nosso "Frametime", que é o tempo que o seu PC leva para atualizar a tela e te mostrar o que mudou ou não!
        double drawInterval = 1000000000/FPS; // 0.016666 seconds? (Sim, se você joga o seu joguinho de Switch 2 a 60 FPS cravados, o tempo que leva para que seu console calcule tudo e te renderize aquela imagem linda do Link nos campos de Hyrule é de 0.1666 segundos.)
        
        //Não sei como tratar muito bem esse aqui, mas saiba (ou pesquise) o que é "Delta Time", e você entende o conceito fácil-fácil.
        double delta = 0;
        //Definições de tempo que eu não saberia explicar com clareza.
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        //O seu querido overlay de FPS. 100% capado a 60, e 100% reduzido a uma linha no console da maquina virtual do Java.
        int drawCount = 0;

        //Se o Thread estiver rodando:...
        while (gameThread != null) {

            //Usa o tempo do sistema para fazer um calculo preciso de quanto tempo a maquina ta demorando para realizar os calculos e renderizar a imagem.
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            /*
            Por mais que pareça mentira, sim, o seu PC está te renderizando em "Modo Software" um programa em Java, 60 vezes por segundo.
            Ou seja, ele te desenha 60 imagens a cada segundo, o que da a impressão de fluídez.
            */
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    //Tudo o que precisa atualizar no jogo, logo os "objetos". (REFIRO-ME AO CLÁSSICO "JAVA OOP")
    public void update() {
        player.update();
    }

    //Método da classe "Graphics" sofrendo uma sobrecarga e depois tendo um "cast" realizado em sí para a mais atualizada e atual classe "Graphics2D".
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); //PRECISA ESTAR ANTES DO PLAYER, PORQUE O JAVA DESENHA TUDO EM ORDEM COMO CAMADAS!!!
        player.draw(g2);

        g2.dispose();
    }
}
