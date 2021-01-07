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
        gridCheck.addActionListener(new ViewGridListener());
        viewMenu.add(gridCheck);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenu simulationMenu = new JMenu("Simulation");
        menuBar.add(simulationMenu);
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(new ResetListener());
        simulationMenu.add(resetItem);
        JMenuItem stepItem = new JMenuItem("Step");
        simulationMenu.add(stepItem);
        stepItem.addActionListener(new StepListener());
        JMenuItem setRuleItem = new JMenuItem("Set Rules");
        setRuleItem.addActionListener(new SetRuleListener());
        simulationMenu.add(setRuleItem);
        simulationMenu.add(new JSeparator());
        JCheckBox wrapAround = new JCheckBox("Wrap Around");
        wrapAround.addActionListener(new WrapAroundListener());
        simulationMenu.add(wrapAround);


        Board board = new Board(256, 256);
        simulation = new Simulation(board);

        try {
            simulation.setRules("B3/S23");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());

            return;
        }

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

    static class SetRuleListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            boolean inputValid = false;

            while(!inputValid) {
                JOptionPane pane = new JOptionPane();
                String str = (String) JOptionPane.showInputDialog(null, "Enter a rule set:", "Set Simulation Rules",
                        JOptionPane.PLAIN_MESSAGE, null, null, "");

                if(str == null)
                    break;

                try {
                    simulation.setRules(str);
                    inputValid = true;
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid format for rule string",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    static class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            simulation.reset();
            boardComponent.repaint();
        }
    }

    static class ViewGridListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            boardComponent.setGridVisible(!boardComponent.getGridVisible());
            boardComponent.repaint();
        }
    }

    static class WrapAroundListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            simulation.setWrapAround(!simulation.getWrapAround());
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
