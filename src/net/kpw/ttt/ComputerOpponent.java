package net.kpw.ttt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ComputerOpponent {

    private Mark mark = Mark.NONE;

    private List<LinkedList<IntPair>> strategies = new LinkedList<LinkedList<IntPair>>();

    public ComputerOpponent(Mark mark) throws RuntimeException {
        if (mark == Mark.NONE) {
            // TODO replace with specific exception type
            throw new RuntimeException("ComputerOpponent has invalid Mark type: " + mark);
        }
        this.mark = mark;
        initStrategies();
    }

    public void move(TicTacToeRectangle[][] board) {
        List<IntPair> strategy = chooseStrategy(board);
        outer: for (IntPair pair : strategy) {
            System.out.println("Strategy: " + pair.getX() + "," + pair.getY());
            TicTacToeRectangle rectangle = board[pair.getX()][pair.getY()];
            if (!rectangle.isOccupied()) {
                try {
                    rectangle.occupy(mark);
                    break outer;
                } catch (IllegalMoveException e) {
                    System.out.println("Illegal move");
                }
            }
        }
    }

    private List<IntPair> chooseStrategy(TicTacToeRectangle[][] board) {
        int counter = 1;
        // List<LinkedList<IntPair>> chosenStrategies = new
        // ArrayList<LinkedList<IntPair>>();
        Iterator<LinkedList<IntPair>> stratIter = strategies.iterator();
        while (stratIter.hasNext()) {
            LinkedList<IntPair> strategy = stratIter.next();
            nextStrategy: for (IntPair pair : strategy) {
                TicTacToeRectangle rectangle = board[pair.getX()][pair.getY()];
                if (rectangle.getMark() == Mark.CIRCLE) {
                    System.out.println("strategy" + counter + " invalid");
                    stratIter.remove();
                    break nextStrategy;
                }
                // chosenStrategies.add(strategy);
            }
            counter++;
        }
        int index = (int) (Math.random() * strategies.size());
        return strategies.get(index);
        // return chosenStrategies.get(index);
    }

    private void initStrategies() {
        // strategy1r = 1st row
        LinkedList<IntPair> strategy = new LinkedList<IntPair>();
        IntPair ip = new IntPair(0, 0);
        strategy.add(ip);
        ip = new IntPair(1, 0);
        strategy.add(ip);
        ip = new IntPair(2, 0);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy2r = 2nd row
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(0, 1);
        strategy.add(ip);
        ip = new IntPair(1, 1);
        strategy.add(ip);
        ip = new IntPair(2, 1);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy3r = 3rd row
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(0, 2);
        strategy.add(ip);
        ip = new IntPair(1, 2);
        strategy.add(ip);
        ip = new IntPair(2, 2);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy1c = 1st column
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(0, 0);
        strategy.add(ip);
        ip = new IntPair(0, 1);
        strategy.add(ip);
        ip = new IntPair(0, 2);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy2c = 2nd column
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(1, 0);
        strategy.add(ip);
        ip = new IntPair(1, 1);
        strategy.add(ip);
        ip = new IntPair(1, 2);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy3c = 3rd column
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(2, 0);
        strategy.add(ip);
        ip = new IntPair(2, 1);
        strategy.add(ip);
        ip = new IntPair(2, 2);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy1d = top left - bottom right diagonal column
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(0, 0);
        strategy.add(ip);
        ip = new IntPair(1, 1);
        strategy.add(ip);
        ip = new IntPair(2, 2);
        strategy.add(ip);
        strategies.add(strategy);

        // strategy2d = top right - bottom left diagonal column
        strategy = new LinkedList<IntPair>();
        ip = new IntPair(2, 0);
        strategy.add(ip);
        ip = new IntPair(1, 1);
        strategy.add(ip);
        ip = new IntPair(0, 2);
        strategy.add(ip);
        strategies.add(strategy);
    }
}
