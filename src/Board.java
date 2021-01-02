import java.util.ArrayList;

public class Board {
    private boolean grid[][];
    private int width;
    private int height;

    public Board(int width, int height)
    {
        this.width = width;
        this.height = height;

        grid = new boolean[height][width];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public ArrayList<Cell> getNeighbors(int x, int y)
    {
        ArrayList<Cell> list = new ArrayList<Cell>();

        list.add(getNorth(x, y));
        list.add(getNorthEast(x, y));
        list.add(getEast(x, y));
        list.add(getSouthEast(x, y));
        list.add(getSouth(x, y));
        list.add(getSouthWest(x, y));
        list.add(getWest(x, y));
        list.add(getNorthWest(x, y));

        return list;

    }

    public ArrayList<Cell> getNeighbors(Cell cell)
    {
        return getNeighbors(cell.getX(), cell.getY());
    }

    public Cell getCell(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return null;

        return new Cell(x, y, grid[y][x]);
    }

    public void setCellState(Cell cell, boolean state)
    {
        grid[cell.getY()][cell.getX()] = state;
        cell.setState(state);
    }

    public Cell getNorth    (int x, int y) { return getCell(x, y - 1); }
    public Cell getNorthEast(int x, int y) { return getCell(x + 1, y - 1); }
    public Cell getEast     (int x, int y) { return getCell(x +1, y);}
    public Cell getSouthEast(int x, int y) { return getCell(x + 1, y + 1);}
    public Cell getSouth    (int x, int y) { return getCell(x, y + 1);}
    public Cell getSouthWest(int x, int y) { return getCell(x - 1, y + 1); }
    public Cell getWest     (int x, int y) { return getCell(x - 1, y);}
    public Cell getNorthWest(int x, int y) { return getCell(x - 1, y -1);}
}
