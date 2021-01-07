import java.util.ArrayList;
import java.util.IllegalFormatException;

public class Simulation {
    private Board mainBoard;
    private Board tempBoard;
    private ArrayList<Integer>  bornCounts; //Numbers of neighbors where cells are born,
    private ArrayList<Integer>  stayCounts; //or stay alive

    public Simulation(int width, int height)
    {
        mainBoard = new Board(width, height);
        tempBoard = new Board(width, height);

        bornCounts = new ArrayList<Integer>();
        stayCounts = new ArrayList<Integer>();
    }

    public Simulation(Board board)
    {
        mainBoard = board;
        tempBoard = new Board(mainBoard.getWidth(), mainBoard.getHeight());
        bornCounts = new ArrayList<Integer>();
        stayCounts = new ArrayList<Integer>();
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

    public void reset()
    {
        mainBoard.clear();
        tempBoard.clear();
    }

    public void setWrapAround(boolean val) { mainBoard.setWrapAround(true); }
    public boolean getWrapAround() { return mainBoard.getWrapAround(); }

    //Gets the next state of a cell using the current rule set
    public boolean getNextState(Cell cell)
    {
        int numAliveNeighbors = 0;

        //count the number of alive neighbors for
        for(Cell i : mainBoard.getNeighbors(cell)) {
            if(i == null) continue;

            if(i.isAlive())
                numAliveNeighbors++;
        }

        //If the cell is alive, check if the number of alive neighbors is in the 'stay' set. If it is,
        //the cell stays alive. If the cell is dead, and the number of alive neighbors is in the 'born' set,
        //then the cell is alive next generation. All else, the cell dies.
        if(cell.isAlive())
        {
            for(int val : stayCounts)
                if(numAliveNeighbors == val)
                    return true;

        }
        else
        {
            for(int val : bornCounts)
                if(numAliveNeighbors == val)
                    return true;
        }

        return false;
    }

    //Copies the data from the tempBoard to the main board
    private void swapBoards()
    {
        for(int y = 0; y < mainBoard.getHeight(); y++)
            for(int x = 0; x < mainBoard.getWidth(); x++)
                mainBoard.setCell(x, y, tempBoard.getCell(x, y).isAlive());
    }

    public void setRules(String str) throws Exception {
        bornCounts.clear(); //Clear the lists for the rules before we add the new ones
        stayCounts.clear();

        String regEx = "(B|b)[0-9]*/(S|s)[0-9]*"; //Reg ex to test if the input string is of the format:
                                                  // B<list of numbers>/S<list of numbers>
        if(!str.matches(regEx))
            throw new Exception("Rule string incorrectly formatted");

        //Get the number lists from each of the rule sets
        int slashIndex = str.indexOf('/');
        String bornStr = str.substring(1, slashIndex);
        String stayStr = str.substring(slashIndex + 2);

        //add each number in the born rule set to the list for born numbers
        for(int i = 0; i < bornStr.length(); i++) {
            int val = Integer.parseInt(bornStr.substring(i, i + 1));

            bornCounts.add(val);
        }

        //add each number in the stay rule set to the list for stay numbers.
        for(int i = 0; i < stayStr.length(); i++) {
            int val = Integer.parseInt(stayStr.substring(i, i + 1));

            stayCounts.add(val);
        }
    }
}