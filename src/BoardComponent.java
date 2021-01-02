import javax.swing.*;
import java.awt.*;

public class BoardComponent extends JComponent {
    private Board board;

    public BoardComponent(Board board)
    {
        this.board = board;

        this.setPreferredSize(new Dimension(720, 480));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        g.fillRect(0, 0, getWidth(), getHeight());

        for(int y = 0; y < board.getHeight(); y++)
        {
            for(int x = 0; x < board.getWidth(); x++)
            {
                Cell curCell = board.getCell(x, y);

                if(curCell.isAlive())
                    g.setColor(new Color(255,255, 128));
                else
                    g.setColor(new Color(128, 128, 128));

                g.fillRect(x*8, y*8, 8, 8);
            }
        }
    }



}
