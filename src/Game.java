
import Exceptions.InvalidMapException;
import Map.*;
import Map.Occupant.Crate;
import Map.Occupiable.DestTile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Holds the necessary components for running the game.
 */
public class Game {

    private Map m;
    private int numRows;
    private int numCols;
    private char[][] rep;

    /**
     * Loads and reads the map line by line, instantiates and initializes Map m.
     * Print out the number of rows, then number of cols (on two separate lines).
     *
     * @param filename the map text filename
     * @throws InvalidMapException
     */
    public void loadMap(String filename) throws InvalidMapException {
        //TODO
        try{
            File readFile = new File(filename);
            Scanner sc = new Scanner(readFile);
            numRows = Integer.parseInt(sc.nextLine());
            numCols = Integer.parseInt(sc.nextLine());
            rep = new char[numRows][numCols];
            String currentLine;
            int currentRowIndex = 0;
            while (sc.hasNextLine()){
                currentLine = sc.nextLine();
                for (int j = 0; j < currentLine.length(); j++){
                    rep[currentRowIndex][j] = currentLine.charAt(j);
                }
                currentRowIndex++;
            }
            m = new Map();
            m.initialize(numRows,numCols,rep);
            sc.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File not found!");
        } catch (InvalidMapException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            System.out.println(numRows);
            System.out.println(numCols);
        }
    }

    /**
     * Can be done using functional concepts.
     * @return Whether or not the win condition has been satisfied
     */
    public boolean isWin() {
        //TODO
        for (DestTile d: m.getDestTiles()){
            if (!d.isCompleted()){
                return false;
            }
        }
        return true; // You may also modify this line.
    }

    /**
     * When no crates can be moved but the game is not won, then deadlock has occurred.
     *
     * @return Whether deadlock has occurred
     */
    public boolean isDeadlocked() {
        //TODO
        boolean horizontal = true;
        boolean vertical = true;
        int unmovable = 0;
        for(Crate crate: m.getCrates()){
            if (m.isOccupiableAndNotOccupiedWithCrate(crate.getR()-1, crate.getC()) && m.isOccupiableAndNotOccupiedWithCrate(crate.getR()+1, crate.getC())){
                vertical = false;
            }
            if (m.isOccupiableAndNotOccupiedWithCrate(crate.getR(), crate.getC() -1) && m.isOccupiableAndNotOccupiedWithCrate(crate.getR(),crate.getC() +1)){
                horizontal = false;
            }
            if (!(vertical || horizontal)){
                unmovable++;
            }
        }

        return (unmovable == m.getCrates().size()); // You may also modify this line.
    }

    /**
     * Print the map to console
     */
    public void display() {
        //TODO
        for (var cR: m.getCells()){
            for (var cC: cR){
                System.out.print(cC.getRepresentation());
            }
            System.out.println();
        }
    }

    /**
     * @param c The char corresponding to a move from the user
     *          w: up
     *          a: left
     *          s: down
     *          d: right
     *          r: reload map (resets any progress made so far)
     * @return Whether or not the move was successful
     */
    public boolean makeMove(char c) {
        //TODO
        switch (c){
            case 'r':
                try {
                    m.initialize(numRows,numCols,rep);
                } catch (InvalidMapException e){
                    e.fillInStackTrace();
                    return false;
                } finally {}
                return true;
            case 'w':
                return m.movePlayer(Map.Direction.UP);
            case 's':
                return m.movePlayer(Map.Direction.DOWN);
            case 'a':
                return m.movePlayer(Map.Direction.LEFT);
            case 'd':
                return m.movePlayer(Map.Direction.RIGHT);
                default:
                    return false;
        }
        // You may also modify this line.
    }


}
