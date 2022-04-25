import javax.swing.*;

public class SnakeGUI {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Snake Game");
        Snake snake = new Snake();
        frame.add(snake);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
