import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOfLife
{
    private static Simulation simulation;
    private static BoardComponent boardComponent;

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

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        JCheckBox gridCheck = new JCheckBox("Show Grid");
        viewMenu.add(gridCheck);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenu simulationMenu = new JMenu("Simulation");
        menuBar.add(simulationMenu);
        JMenuItem stepItem = new JMenuItem("Step");
        simulationMenu.add(stepItem);
        stepItem.addActionListener(new StepListener());


        Board board = new Board(256, 256);
        board.setCell(1, 0, true);
        board.setCell(2, 1, true);
        board.setCell(0, 2, true);
        board.setCell(1, 2, true);
        board.setCell(2, 2, true);

        simulation = new Simulation(board);

        boardComponent = new BoardComponent(simulation.getBoard());
        frame.add(boardComponent);

        frame.addKeyListener(new KeyboardListener());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    static class StepListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            simulation.step();
            boardComponent.repaint();
        }
    }

    static class KeyboardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == ' ') {
                simulation.step();
                boardComponent.repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
