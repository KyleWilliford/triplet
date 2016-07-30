package net.kpw.ttt;

import org.newdawn.slick.geom.Rectangle;

/**
 * An extension of the Slick2d {@link Rectangle} class, with added abilities for
 * tracking user marking data.
 */
public class TicTacToeRectangle extends Rectangle {

    /**
     * Generated serial UID
     */
    private static final long serialVersionUID = 3034377437771692260L;

    /**
     * Marks this rectangle as being occupied.
     */
    private boolean occupied = false;

    /**
     * The mark that this rectangle contains.
     */
    private Mark mark = Mark.NONE;

    public TicTacToeRectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * return the {@link Mark} contained in this rectangle
     *
     * @return {@link Mark}
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Return the boolean value corresponding to whether or not this rectangle
     * is occupied.
     *
     * @return a boolean, true if this rectangle is occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Mark this rectangle as being occupied, and store the Mark for win
     * condition checking later.
     *
     * @param mark
     *            {@link Mark}
     * @throws IllegalMoveException
     *             if this rectangle has already been selected.
     */
    public void occupy(Mark mark) throws IllegalMoveException {
        if (occupied) {
            throw new IllegalMoveException("I'm sorry Dave, I'm afraid I can't do that.");
        }
        occupied = true;
        this.mark = mark;
    }
}
