package net.kpw.ttt;

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

    public void move(TicTacToeRectangle[][] board, int openSpaces) {
        List<IntPair> strategy = chooseStrategy(board, openSpaces);
        outer: for (IntPair pair : strategy) {
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

    private List<IntPair> chooseStrategy(TicTacToeRectangle[][] board, int openSpaces) {

        List<LinkedList<IntPair>> validStrategies = new LinkedList<LinkedList<IntPair>>();
        for (LinkedList<IntPair> strategy : strategies) {
            int playerMarkCounter = 0;
            int aiMarkCounter = 0;
            for (IntPair pair : strategy) {
                TicTacToeRectangle rectangle = board[pair.getX()][pair.getY()];
                if (rectangle.getMark() == Mark.CIRCLE) {
                    ++playerMarkCounter;
                }
                if (rectangle.getMark() == Mark.CROSS) {
                    ++aiMarkCounter;
                }
            }
            if (playerMarkCounter == 2 && aiMarkCounter == 0) {
                System.out.println("BLOCKING STRATEGY");
                return strategy;
            }
            if (playerMarkCounter == 0 || openSpaces < 3) {
                validStrategies.add(strategy);
            }
        }
        int index = (int) (Math.random() * validStrategies.size());
        return validStrategies.get(index);
    }

    /**
     * Basic strategies for AI opponent. These are a set of coordinates listed
     * for the AI to determine if one or more are valid strategies.
     */
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

        /*
         * Reverse the order of coordinates for all strategies, and add each list of reversed coordinates as a new strategy. 
         * Ex. strategy 3c is still the same coordinates as 3c, but the first pair of coordnates will be places in the reverse list as 
         * the third pair of coordinates.
         */
        List<LinkedList<IntPair>> reverseStrategies = new LinkedList<LinkedList<IntPair>>();
        for (LinkedList<IntPair> normStrat : strategies) {
            strategy = new LinkedList<IntPair>();
            IntPair first = normStrat.get(normStrat.size() - 1);
            IntPair middle = normStrat.get(normStrat.size() / 2);
            IntPair last = normStrat.get(0);
            strategy.add(first);
            strategy.add(middle);
            strategy.add(last);
            reverseStrategies.add(strategy);
        }
        strategies.addAll(reverseStrategies);
    }
}
