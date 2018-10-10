package Map.Occupiable;

import Map.Occupant.Crate;

import java.nio.file.DirectoryStream;
import java.util.function.Function;
import java.lang.Object;

/**
 * A destination tile. To win the game, we must push the crate with the corresponding ID onto this tile
 */
public class DestTile extends Tile {
    private char destID;

    /**
     * @param destID The destination uppercase char corresponding to a crate with the same lowercase letter
     */
    public DestTile(char destID) {
        this.destID = destID;
    }

    /**
     * @return Whether or not this destination tile has been completed, i.e. a crate with the matching lowercase letter
     * is currently occupying this tile.
     */
    public boolean isCompleted() {
        //TODO
        if (super.getOccupant().isPresent()){
            if (super.getOccupant().get() instanceof Crate){
                 if (Character.toLowerCase(destID) == ((Crate) super.getOccupant().get()).getID()){
                     return true;
                 }
            }
        }
        return false; // You may also modify this line.
    }

    /**
     * @return The uppercase letter corresponding to the crate with the matching lowercase letter
     */
    private char getDestID() {
        return destID;
    }

    @Override
    public char getRepresentation() {
        //TODO
        if (super.getOccupant().isPresent()){
            return super.getOccupant().get().getRepresentation();
        }
        return getDestID(); // You may also modify this line.
    }
}
