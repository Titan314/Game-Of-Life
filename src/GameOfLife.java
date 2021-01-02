import javax.swing.*;
import java.awt.*;

public class GameOfLife
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Game Of Life");

        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.PAGE_START);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem newItem = new JMenuItem("New");
        fileMenu.add(newItem);
        JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(loadItem);
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
