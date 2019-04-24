public interface BotAPI {

    String getName();

    String getCommand(Plays possiblePlays);

    String getDoubleDecision();
    
    void setWeights(int[] weights);
}
