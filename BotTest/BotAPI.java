public interface BotAPI {

    String getName();

    String getCommand(Plays possiblePlays);

    String getDoubleDecision();
    
    int getPipCountDifference();
    
    int getBlockBlotDifference();
    
    int getNumHomeBoardBlocks();
    
    int getEscapedCheckers();
    
    int getLastOpponentChecker();
    
    int getNumHomeCheckers();
    
    int getNumAnchors();
    
    int getNumCheckersBearOff();
}
