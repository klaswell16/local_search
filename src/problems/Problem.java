package problems;

//S is data type of states

public interface Problem<S> {
    //given the current state, generate a random child state
    public S generateNewState(S current);

    public double cost(S state);

    public S getInitState();

    public double goalCost();
}
