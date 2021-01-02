public class Cell {
    private int x;
    private int y;
    private boolean alive;

    public Cell(int x, int y, boolean alive)
    {
        this.x = x;
        this.y = y;
        this.alive = alive;
    }

    public boolean isAlive() { return alive; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void setState(boolean state) { alive = state; }
}
