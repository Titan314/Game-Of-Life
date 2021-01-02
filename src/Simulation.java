public class Simulation {
    private Board mainBoard;
    private Board tempBoard;

    public Simulation(int width, int height)
    {
        mainBoard = new Board(width, height);
        tempBoard = new Board(width, height);
    }

    public Board getBoard()
    {
        return mainBoard;
    }

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

        if(cell.isAlive() && (numAliveNeighbors < 2 || numAliveNeighbors > 3))
            return true;
        else if (numAliveNeighbors == 3)
            return true;
        else
            return false;
    }

    private void swapBoards()
    {
        Board swap = mainBoard;
        mainBoard = tempBoard;
        tempBoard = swap;
    }
}
