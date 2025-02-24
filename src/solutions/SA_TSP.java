package solutions;

import problems.TSP;

public class SA_TSP {
    public static void main(String[] args) {
        int sample = 5;
        TSP tsp = new TSP(sample);
        int[] initialState = tsp.getInitState();
        tsp.printState(initialState);

        int[] newState = tsp.generateNewState(initialState);
        tsp.printState(newState);
    }
}
