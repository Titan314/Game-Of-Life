public class Simulation {
    private Board mainBoard;
    private Board tempBoard;

    public Simulation(int width, int height)
    {
        mainBoard = new Board(width, height);
        tempBoard = new Board(width, height);
    }

    public Simulation(Board board)
    {
        mainBoard = board;
        tempBoard = new Board(mainBoard.getWidth(), mainBoard.getHeight());
    }


    public Board getBoard()
    {
        return mainBoard;
    }

    //Todo: Optimize. This code is currently O(n^2), and large board sizes get slow.
    //      Ideas:
    //      * keep track of the left and right-most cells and process only the
    //        given square.
    //      * Maintain a list of the cells that are alive, and process their neighbors
    //      * Multithreading?
    public void step()
    {
        for(int y = 0; y < mainBoard.getHeight(); y++) {
            for(int x = 0; x < mainBoard.getWidth(); x++) {
                Cell curCell = mainBoard.getCell(x, y);

                boolean nextState = getNextState(curCell);

                tempBoard.setCellState(curCell, nextState);
            }
        }

        swapBoards();
    }

    public boolean getNextState(Cell cell)
    {
        int numAliveNeighbors = 0;

        //count the number of alive neighbors for
        for(Cell i : mainBoard.getNeighbors(cell)) {
            if(i == null) continue;

            if(i.isAlive())
                numAliveNeighbors++;
        }

        if(cell.isAlive() && (numAliveNeighbors == 2 || numAliveNeighbors == 3))
            return true;
        else if (numAliveNeighbors == 3)
            return true;
        else
            return false;
    }

    private void swapBoards()
    {
        for(int y = 0; y < mainBoard.getHeight(); y++)
            for(int x = 0; x < mainBoard.getWidth(); x++)
                mainBoard.setCell(x, y, tempBoard.getCell(x, y).isAlive());
    }
}
