package Main;

import javax.swing.JFrame;

public class Main {
    
    /*
    
        Eu adoraria dar uma explicada no que ta acontecendo aqui depois de ter estudado essas relações do Swing
        e demais """EXTENSÕES""" do Java, voltados à interfaces gráficas de usuário. (GUI)
        ------------------
        Enfim, este arquivo é o que puxa tudo no lugar, nosso querido "método main"!
        Aqui ocorre a configuração da janela, o nosso "JFrame" lá em cima.
        
        Assim como ocorre a criação do objeto do nosso GamePanel. (Que é um "JPanel" mas BEM CHIQUE)
        E é isso no "Main.java"!
    */

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("How are u? :3");

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game2D");

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamepanel.startGameThread();
    }
}