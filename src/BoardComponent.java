import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

public class BoardComponent extends JComponent implements MouseWheelListener,
        MouseInputListener
{
    private Board board;
    private boolean drawGrid = true;

    private Point mousePoint;

    //variables for drawing the view
    private float zoomFactor;
    private float offsetX;
    private float offsetY;

    private Point lastModified;

    public BoardComponent(Board board)
    {
        super();

        this.board = board;
        this.lastModified = new Point(-1, -1);

        this.setPreferredSize(new Dimension(720, 480));
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        zoomFactor = 16.0f;
    }

    //Todo: Optimize so that only the cells on screen are drawn.
    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(new Color(64, 64, 64));
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

                int width = (int)zoomFactor;
                int scale = width;

                int drawX = scale * x + (int)offsetX;
                int drawY = scale * y + (int)offsetY;

                if(drawGrid && scale > 1)
                    width -= 1;

                g.fillRect(drawX, drawY, width, width);
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = e.getWheelRotation() * e.getScrollAmount();

        Point scrollPoint = e.getPoint();

        float newZoom = zoomFactor - (float)scroll;
        float delZoom = newZoom - zoomFactor;
        if(newZoom < 1.0f)
            newZoom = 1.0f;



        zoomFactor = newZoom;

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int panOnMask = e.BUTTON3_DOWN_MASK;
        int panOffMask = e.BUTTON1_DOWN_MASK | e.BUTTON2_DOWN_MASK;
        int drawOnMask = e.BUTTON1_DOWN_MASK;
        int drawOffMask = e.BUTTON2_DOWN_MASK | e.BUTTON3_DOWN_MASK;

        if((e.getModifiersEx() & (panOnMask | panOffMask)) == panOnMask) {


            Point newPoint = e.getPoint();
            offsetX += newPoint.x - mousePoint.x;
            offsetY += newPoint.y - mousePoint.y;

            mousePoint = e.getPoint();

            repaint();
        }

        if((e.getModifiersEx() & (drawOnMask | drawOffMask)) == drawOnMask) {
            Point point = e.getPoint();

            int cellX = (int)((float)(point.x - offsetX) / zoomFactor);
            int cellY = (int)((float)(point.y - offsetY) / zoomFactor);

            if(cellX != lastModified.x || cellY != lastModified.y)
            {
                Cell curCell = board.getCell(cellX, cellY);
                board.setCellState(curCell, !curCell.isAlive());

                repaint();

                lastModified = new Point(cellX, cellY);
            }

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == e.BUTTON1)
        {
            Point point = e.getPoint();

            int cellX = (int)((float)(point.x - offsetX) / zoomFactor);
            int cellY = (int)((float)(point.y - offsetY) / zoomFactor);

            Cell curCell = board.getCell(cellX, cellY);
            board.setCellState(curCell, !curCell.isAlive());

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        mousePoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
