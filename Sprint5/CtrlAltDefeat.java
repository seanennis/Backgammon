import java.util.ArrayList;
import java.util.Random;

public class CtrlAltDefeat implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me, opponent;
    private BoardAPI board;
    private CubeAPI cube;
    private MatchAPI match;
    private InfoPanelAPI info;
    private int[] weights = new int[8];

    CtrlAltDefeat(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "CtrlAltDefeat"; // must match the class name
    }
    
    public void setWeights(int[] weights) {
    	this.weights = weights;
    }

    public String getCommand(Plays possiblePlays) {
    	String cmd;
    	/*
    	if(getEvaluation(board.get(),1,1,1,1,1,1,1,1) >= 0 && match.canDouble(me.getId())) {
    		cmd = "double";
    	}
    	else { */
    	if(canBearOff()) {
    		if(checkIfOpponentCheckersInHome())
    			cmd = String.valueOf(opposedBearOff(possiblePlays));
    		else
    			cmd = String.valueOf(unopposedBearOff(possiblePlays));
    	}
    	else {
    		cmd = String.valueOf(getMove(possiblePlays));
    	}
    	//}
    	return cmd;
    }
    
    public int getMove(Plays possiblePlays) {

        int[][] checkerLayoutAfterMove = new int[2][26];
        
        ArrayList<Double> probability = new ArrayList<>();
        
        //iterate through all the possible plays
        for(int i = 0;i < possiblePlays.number();i++) {
            checkerLayoutAfterMove = board.get();
            Play currentPlay = possiblePlays.get(i);
            for(int j = 0;j < currentPlay.numberOfMoves();j++) {
                Move currentMove = currentPlay.getMove(j);
                checkerLayoutAfterMove[0][currentMove.getFromPip()]--;
                checkerLayoutAfterMove[0][currentMove.getToPip()]++;
                if(currentMove.isHit()) {
                    checkerLayoutAfterMove[1][currentMove.getToPip()]--;
                }
            }
            
            // finds value of each move and adds to the array probability
            probability.add(getEvaluation(checkerLayoutAfterMove, weights[0], weights[1], weights[2], weights[3], weights[4], weights[5], weights[6], weights[7]));  // weights can be altered here
        }
        
        int choice = 0;
        Double highestProbability = probability.get(0);
        
        for(int i = 0;i < probability.size();i++)
            if(probability.get(i) >= highestProbability) {
                highestProbability = probability.get(i);
                choice = i + 1;
            }
        
    	return choice;
    }
    
    public double getEvaluation(int[][] boardLayout, int pipCountDifferenceWeight, int blockBlotDifferenceWeight, int numHomeBoardBlocksWeight,
    		int escapedCheckersWeight, int numHomeCheckersWeight, int numAnchorsWieght, int numCheckersBearOffWeight, int primeFeatureWeight) {
    	
    	int Evaluation = pipCountDifferenceWeight*getPipCountDifference(boardLayout) + blockBlotDifferenceWeight*getBlockBlotDifference(boardLayout) + 
    			numHomeBoardBlocksWeight*getNumHomeBoardBlocks(boardLayout) + escapedCheckersWeight*getEscapedCheckers(boardLayout) + numHomeCheckersWeight*getNumHomeCheckers(boardLayout) + 
    			numAnchorsWieght*getNumAnchors(boardLayout) + numCheckersBearOffWeight*getNumCheckersBearOff(boardLayout) + primeFeatureWeight*primeFeature(boardLayout);
    	
    	return Evaluation;
    }
    
    //TODO test
    public int getPipCountDifference(int[][] boardLayout) {
    	int pipCountP0 = 0;
    	int pipCountP1 = 1;
    	for(int i = 1; i <= 25; i++) {
    		pipCountP0 += boardLayout[0][i] * i;
    		pipCountP1 += boardLayout[1][i] * i;
    	}
    	
    	return pipCountP0 - pipCountP1;
    }
    
    public int unopposedBearOff(Plays possiblePlays)
    {
    	int[][] checkerLayoutAfterMove = new int[2][26];
        
        ArrayList<Double> probability = new ArrayList<>();
        
        //iterate through all the possible plays
        for(int i = 0;i < possiblePlays.number();i++) {
            checkerLayoutAfterMove = board.get();
            Play currentPlay = possiblePlays.get(i);
            for(int j = 0;j < currentPlay.numberOfMoves();j++) {
                Move currentMove = currentPlay.getMove(j);
                checkerLayoutAfterMove[0][currentMove.getFromPip()]--;
                checkerLayoutAfterMove[0][currentMove.getToPip()]++;
                if(currentMove.isHit()) {
                    checkerLayoutAfterMove[1][currentMove.getToPip()]--;
                }
            }
            
            // finds value of each move and adds to the array probability
            probability.add(getEvaluation(checkerLayoutAfterMove, 0,0,0,0,0,0,1,0));  // weights can be altered here
        }
        
        int choice = 0;
        Double highestProbability = probability.get(0);
        
        for(int i = 0;i < probability.size();i++)
            if(probability.get(i) >= highestProbability) {
                highestProbability = probability.get(i);
                choice = i + 1;
            }
        
    	return choice;
    }
    
    public int opposedBearOff(Plays possiblePlays)
    {
    	int[][] checkerLayoutAfterMove = new int[2][26];
        
        ArrayList<Double> probability = new ArrayList<>();
        
        //iterate through all the possible plays
        for(int i = 0;i < possiblePlays.number();i++) {
            checkerLayoutAfterMove = board.get();
            Play currentPlay = possiblePlays.get(i);
            for(int j = 0;j < currentPlay.numberOfMoves();j++) {
                Move currentMove = currentPlay.getMove(j);
                checkerLayoutAfterMove[0][currentMove.getFromPip()]--;
                checkerLayoutAfterMove[0][currentMove.getToPip()]++;
                if(currentMove.isHit()) {
                    checkerLayoutAfterMove[1][currentMove.getToPip()]--;
                }
            }
            
            // finds value of each move and adds to the array probability
            probability.add(getEvaluation(checkerLayoutAfterMove,0,10,0,0,0,0,1,0));  // weights can be altered here
        }
        
        int choice = 0;
        Double highestProbability = probability.get(0);
        
        for(int i = 0;i < probability.size();i++)
            if(probability.get(i) >= highestProbability) {
                highestProbability = probability.get(i);
                choice = i + 1;
            }
        
    	return choice;
    }
    
    public boolean checkIfOpponentCheckersInHome() {
    	boolean checker = false;
    	
    	for(int i = 19; i <= 24; i++) {
    		board.getNumCheckers(opponent.getId(), i);
    	}
    	
    	return checker;
    }
    
    public boolean canBearOff() {
    	boolean bearOff = true;
    	for(int i = 7; i <= 25; i++) {
    		if(board.getNumCheckers(me.getId(), i) > 0)
    			bearOff = false;
    	}
    	
    	return bearOff;
    }
    
    //TODO test
    public int getBlockBlotDifference(int[][] boardLayout) {
    	int block = 0;
    	int blot = 1;
    	for(int i = 1; i < 25; i++) {
    		if(boardLayout[0][i] >= 2)
    			block ++;
    		else if(boardLayout[0][i] == 1)
    			blot++;
    	}
    	
    	return block - blot;
    }
    
    //TODO test
    // weights added, most important points in descending order: 6, 5, 4, 3, 2, 1
    public int getNumHomeBoardBlocks(int[][] boardLayout) {
    	int homeBlocks = 0;
    	for(int i = 1; i <= 6; i++) {
    		if(boardLayout[0][i] >= 2)
    			homeBlocks += i;
    	}
    
    	return homeBlocks;
    }
    
    public int getEscapedCheckers(int[][] boardLayout) {
    	int escapedCheckers = 0;
    	for(int i = getLastOpponentChecker(boardLayout); i > 0; i--) {
    		escapedCheckers += boardLayout[0][i];
    	}
    	
    	return escapedCheckers;
    }
    
    public int getLastOpponentChecker(int[][] boardLayout) {
    	int pip = 0;
    	for(int i = 0; i < 25; i++) {
    		if(boardLayout[1][i] >= 1)
    			pip = i;
    	}
    	pip = 25 - pip;
    	
    	return pip;
    }
    
    public int getNumHomeCheckers(int[][] boardLayout)
    {
    	int homeCheckers = 0;
    	for(int i = 1; i <= 6; i++) 
    	{
    		if(boardLayout[0][i] >= 1)
    		{
    			homeCheckers += boardLayout[0][i];
    		}
    	}
    	
    	return homeCheckers;
    }
    
    // weighted by how advanced the anchors are on the board
    public int getNumAnchors(int[][] boardLayout) {
    	int numAnchors = 0;
    	for(int i = 19; i <= 24; i++) {
    		if(boardLayout[0][i] >= 2)
    			numAnchors += 25 - i;
    	}
    	
    	return numAnchors;
    }
    
    public int getNumCheckersBearOff(int[][] boardLayout)
    {
    	int checkersBearOff = 0;
    	
    	if(boardLayout[0][0] >= 1)
		{
			checkersBearOff += boardLayout[0][0];
		}
    	
    	return checkersBearOff;
    }

    public int primeFeature(int[][] boardLayout) {

        int primeCounter = 0,primeScore = 0;
        boolean previousHadBlock = false;
        
        
        for(int i = 1;i < 25;i++) {
            if(previousHadBlock) {
                if(boardLayout[0][i] >= 2) {
                    primeCounter++;
                }
                else {
                    if(primeCounter != 1) {
                        primeScore += primeCounter;
                    }
                    primeCounter = 0;
                    previousHadBlock = false;
                }
            }
            else {
                if(boardLayout[0][i] >= 2) {
                    primeCounter = 1;
                    previousHadBlock = true;
                }
            }
        }
        return primeScore;
    }

    public String getDoubleDecision()
    {
    	String playDouble;
    	double probability = getEvaluation(board.get(),1,1,1,1,1,1,1,1);
    	
    	if(probability >= 0)
    	{
    		playDouble = "y";
    	}
    	else
    	{
    		playDouble = "n";
    	}
        // Add your code here
        return playDouble;
    }
}
