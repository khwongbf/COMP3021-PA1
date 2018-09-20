package Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Occupant.Crate;
import Map.Occupant.Occupant;
import Map.Occupant.Player;
import Map.Occupiable.DestTile;
import Map.Occupiable.Occupiable;
import Map.Occupiable.Tile;

import java.util.ArrayList;

/**
 * A class holding a the 2D array of cells, representing the world map
 */
public class Map {
    private Cell[][] cells;
    private ArrayList<DestTile> destTiles = new ArrayList<>();
    private ArrayList<Crate> crates = new ArrayList<>();

    private Player player;

    /**
     * This function instantiates and initializes cells, destTiles, crates to the correct map elements (the # char
     * means a wall, @ the player, . is unoccupied Tile, lowercase letter is crate on a Tile,
     * uppercase letter is an unoccupied DestTile).
     *
     * @param rows The number of rows in the map
     * @param cols The number of columns in the map
     * @param rep  The 2d char array read from the map text file
     * @throws InvalidMapException Throw the correct exception when necessary. There should only be 1 player.
     */
    public void initialize(int rows, int cols, char[][] rep) throws InvalidMapException {
        //TODO
        cells = new Cell[rows][cols];
        Crate nextCrate;
        boolean playerInitialized = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rep[i][j] == '#'){
                    cells[i][j] = new Wall();
                } else if (rep[i][j] == '.'){
                    cells[i][j] = new Tile();
                } else if (rep[i][j] == '@'){
                    if (!playerInitialized){
                        player = new Player(i,j);
                        playerInitialized = true;
                        cells[i][j] = new Tile();
                        ((Tile) cells[i][j]).setOccupant(player);
                    } else{
                        throw new InvalidNumberOfPlayersException("Invalid Number Of Players Exception occurred!");
                    }
                } else if (Character.isUpperCase(rep[i][j])){
                    cells[i][j] = new DestTile(rep[i][j]);
                    destTiles.add((DestTile)cells[i][j]);
                } else if (Character.isLowerCase(rep[i][j])){
                    nextCrate = new Crate(i,j,rep[i][j]);
                    crates.add(nextCrate);
                    cells[i][j] = new Tile();
                    ((Tile) cells[i][j]).setOccupant(nextCrate);
                } else {
                    throw new UnknownElementException("Unknown Element Exception occurred!");
                }
            }
        }
    }

    public ArrayList<DestTile> getDestTiles() {
        return destTiles;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Attempts to move the player in the specified direction. Note that the player only has the strength to push
     * one crate. It cannot push 2 or more crates simultaneously. The player cannot walk through walls or walk beyond
     * map coordinates.
     *
     * @param d The direction the player wants to move
     * @return Whether the move was successful
     */
    public boolean movePlayer(Direction d) {
        //TODO
        boolean movable = true;
        switch (d){
            case UP:
                if (isOccupiableAndNotOccupiedWithCrate(player.getR()-1, player.getC())){
                    ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                    player.setPos(player.getR()-1,player.getC());
                    ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                } else{
                    for (var crate: getCrates()){
                        if (crate.getR() == player.getR()-1 && crate.getC() == player.getC()){
                            if(moveCrate(crate,d)){
                                ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                                player.setPos(player.getR()-1,player.getC());
                                ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                            } else{
                                movable = false;
                            }
                            break;
                        }
                    }
                }
                break;
            case DOWN:
                if (isOccupiableAndNotOccupiedWithCrate(player.getR()+1, player.getC())){
                    ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                    player.setPos(player.getR()+1,player.getC());
                    ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                } else{
                    for (var crate: getCrates()){
                        if (crate.getR() == player.getR()+1 && crate.getC() == player.getC()){
                            if(moveCrate(crate,d)){
                                ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                                player.setPos(player.getR()+1, player.getC());
                                ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                            } else{
                                movable = false;
                            }
                            break;
                        }
                    }
                }
                break;
            case LEFT:
                if (isOccupiableAndNotOccupiedWithCrate(player.getR(), player.getC()-1)){
                    ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                    player.setPos(player.getR(),player.getC()-1);
                    ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                } else{
                    for (var crate: getCrates()){
                        if (crate.getR() == player.getR() && crate.getC() == player.getC() -1){
                            if(moveCrate(crate,d)){
                                ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                                player.setPos(player.getR(), player.getC()-1);
                                ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                            } else{
                                movable = false;
                            }
                            break;
                        }
                    }
                }
                break;
            case RIGHT:
                if (isOccupiableAndNotOccupiedWithCrate(player.getR(), player.getC()+1)){
                    ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                    player.setPos(player.getR(),player.getC()+1);
                    ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                } else{
                    for (var crate: getCrates()){
                        if (crate.getR() == player.getR() && crate.getC() == player.getC() +1){
                            if(moveCrate(crate,d)){
                                ((Tile)getCells()[player.getR()][player.getC()]).removeOccupant();
                                player.setPos(player.getR(), player.getC()+1);
                                ((Tile)getCells()[player.getR()][player.getC()]).setOccupant(player);
                            } else{
                                movable = false;
                            }
                            break;
                        }
                    }
                }
                break;
        }
        return movable; // You may also modify this line.
    }

    /**
     * Attempts to move the crate into the specified direction by 1 cell. Will only succeed if the destination
     * implements the occupiable interface and is not currently occupied.
     *
     * @param c The crate to be moved
     * @param d The desired direction to move the crate in
     * @return Whether or not the move was successful
     */
    private boolean moveCrate(Crate c, Direction d) {
        //TODO
        switch (d){
            case UP:
                if (isOccupiableAndNotOccupiedWithCrate(c.getR()-1, c.getC())){
                    ((Tile)getCells()[c.getR()][c.getC()]).removeOccupant();
                    c.setPos(c.getR()-1, c.getC());
                    ((Tile)getCells()[c.getR()][c.getC()]).setOccupant(c);
                } else {
                    return false;
                }
                break;
            case DOWN:
                if (isOccupiableAndNotOccupiedWithCrate(c.getR()+1, c.getC())){
                    ((Tile)getCells()[c.getR()][c.getC()]).removeOccupant();
                    c.setPos(c.getR()+1, c.getC());
                    ((Tile)getCells()[c.getR()][c.getC()]).setOccupant(c);
                } else {
                    return false;
                }
                break;
            case LEFT:
                if (isOccupiableAndNotOccupiedWithCrate(c.getR(), c.getC() -1)){
                    ((Tile)getCells()[c.getR()][c.getC()]).removeOccupant();
                    c.setPos(c.getR(), c.getC()-1);
                    ((Tile)getCells()[c.getR()][c.getC()]).setOccupant(c);
                } else {
                    return false;
                }
                break;
            case RIGHT:
                if (isOccupiableAndNotOccupiedWithCrate(c.getR(), c.getC() +1)){
                    ((Tile)getCells()[c.getR()][c.getC()]).removeOccupant();
                    c.setPos(c.getR(), c.getC()+1);
                    ((Tile)getCells()[c.getR()][c.getC()]).setOccupant(c);
                } else {
                    return false;
                }
                break;
        }
        return true; // You may also modify this line.
    }

    private boolean isValid(int r, int c) {
        return (r >= 0 && r < cells.length && c >= 0 && c < cells[0].length);
    }

    /**
     * @param r The row coordinate
     * @param c The column coordinate
     * @return Whether or not the specified location on the grid is a location which implements Occupiable,
     * yet does not currently have a crate in it. Will return false if out of bounds.
     */
    public boolean isOccupiableAndNotOccupiedWithCrate(int r, int c) {
        //TODO
        if (!isValid(r,c)|| cells[r][c] instanceof Wall) {
            return false;
        } else if (((Tile) getCells()[r][c]).getOccupant().isPresent()){
            if ((((Tile) getCells()[r][c]).getOccupant().get() instanceof Crate)){
                return false;
            }
        }
        return true; // You may also modify this line.
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
