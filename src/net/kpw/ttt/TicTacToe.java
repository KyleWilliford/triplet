package net.kpw.ttt;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

/**
 * A basic game of TicTacToe.
 */
public class TicTacToe extends BasicGame {

    /**
     * Game over flag.
     */
    private boolean gameOver = false;

    /**
     * Winner of the current game.
     */
    private Mark winner = Mark.NONE;

    /**
     * Max number of horizontal game tiles.
     */
    private int maxX = 3;

    /**
     * Max number of vertical game tiles.
     */
    private int maxY = 3;

    /**
     * The width of each game tile.
     */
    float width = 100F;

    /**
     * The height of each game tile.
     */
    float height = 100F;

    /**
     * The mark for the next player selection.
     */
    private Mark nextMark = Mark.CIRCLE;

    /**
     * The game board matrix.
     */
    private TicTacToeRectangle[][] board = new TicTacToeRectangle[maxX][maxY];

    /**
     * Constructor that accepts the name of the game window.
     *
     * @param title
     *            The game window title.
     */
    public TicTacToe(String title) {
        super(title);
    }

    /**
     * Main thread - starts the Slick2d game container, and sets options for
     * display mode.
     *
     * @param args
     *            command line arguments.
     */
    public static void main(String[] args) {
        try {
            AppGameContainer appgc = new AppGameContainer(new TicTacToe("TripleT"));
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        Graphics g = gc.getGraphics();
        if (!gameOver) {
            float xpos = 50F;
            for (int x = 0; x < maxY; x++) {
                float ypos = 10F;
                for (int y = 0; y < maxX; y++) {
                    TicTacToeRectangle r = new TicTacToeRectangle(ypos, xpos, width, height);
                    board[y][x] = r;
                    System.out.println("Added board rectangle at pos " + x + "," + y);
                    g.draw(r);
                    ypos += width;
                }
                xpos += height;
            }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        System.out.println("button " + button + " at " + x + "," + y);
        if (button == 0 && !gameOver) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j].contains(x, y)) {
                        System.out.println("clicked rectangle at pos " + i + ", " + j);
                        try {
                            if (nextMark == Mark.CIRCLE) {
                                board[i][j].occupy(Mark.CIRCLE);
                                nextMark = Mark.CROSS;
                            } else {
                                board[i][j].occupy(Mark.CROSS);
                                nextMark = Mark.CIRCLE;
                            }
                        } catch (IllegalMoveException e) {
                            System.out.println("Illegal move");
                        }
                    }
                }
            }
            checkGameOver();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (TicTacToeRectangle[] element : board) {
            for (int y = 0; y < board.length; y++) {
                TicTacToeRectangle r = element[y];
                g.draw(r);
                if (r.isOccupied()) {
                    if (r.getMark() == Mark.CIRCLE) {
                        Circle c = new Circle(r.getCenterX(), r.getCenterY(), r.getWidth() / 2);
                        g.draw(c);
                    } else {
                        Vector2f origin = r.getLocation();
                        Vector2f bottomRight = new Vector2f(origin.getX() + width, origin.getY() + height);
                        Line line1 = new Line(origin, bottomRight);

                        Vector2f topRight = new Vector2f(origin.getX() + width, origin.getY());
                        Vector2f bottomLeft = new Vector2f(origin.getX(), origin.getY() + height);
                        Line line2 = new Line(topRight, bottomLeft);

                        g.draw(line1);
                        g.draw(line2);
                    }
                }
            }
        }
        if (gameOver) {
            g.drawString("\nGame over! Winner: " + winner, 10, 10);
            g.drawString("Restart game? (y/n)", 100, 10);
        }
    }

    @Override
    public void update(GameContainer arg0, int arg1) throws SlickException {
    }

    private boolean checkGameOver() {

        for (int i = 0; i < board.length; i++) {
            // check rows
            Mark current = null;
            Mark previous = null;
            for (int j = 0; j < board.length; j++) {
                TicTacToeRectangle r = board[j][i];
                if (r.isOccupied()) {
                    System.out.println("Piece at position " + j + "," + i + " is marked with a " + r.getMark());
                }
                if (current == null || previous == null) {
                    // look at next element
                    previous = current;
                    current = r.getMark();
                } else {
                    if (current == previous) {
                        // look at next element
                        previous = current;
                        current = r.getMark();
                    } else {
                        break;
                    }
                }
            }
            if (current == previous && current != Mark.NONE && current != null) {
                System.out.println("Winner: " + current);
                gameOver = true;
                winner = current;
            }

            // check columns
            current = null;
            previous = null;
            for (int j = 0; j < board.length; j++) {
                TicTacToeRectangle r = board[i][j];
                if (current == null || previous == null) {
                    // look at next element
                    previous = current;
                    current = r.getMark();
                } else {
                    if (current == previous) {
                        // look at next element
                        previous = current;
                        current = r.getMark();
                    } else {
                        break;
                    }
                }
            }
            if (current == previous && current != Mark.NONE && current != null) {
                System.out.println("Winner: " + current);
                gameOver = true;
                winner = current;
            }

            // check diagonals
            // TODO check diagonals procedurally
            if (board[1][1].getMark() != Mark.NONE) {
                if (board[0][0].getMark() == board[1][1].getMark() && board[1][1].getMark() == board[2][2].getMark()) {
                    System.out.println("Winner: " + board[1][1].getMark());
                    gameOver = true;
                    winner = board[1][1].getMark();
                }
                if (board[0][2].getMark() == board[1][1].getMark() && board[1][1].getMark() == board[2][0].getMark()) {
                    System.out.println("Winner: " + board[1][1].getMark());
                    gameOver = true;
                    winner = board[1][1].getMark();
                }
            }
        }
        return false;
    }
}