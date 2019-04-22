
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

    public String getCommand(Plays possiblePlays) {
        // Add your code here
    	System.out.println("Pip Dif: " + getPipCountDifference());
    	System.out.println("Block Blot Dif: " + getBlockBlotDifference());
    	System.out.println("Home Board Blocks: " + getNumHomeBoardBlocks());
    	System.out.println("Home Board Checkers:" + getNumHomeCheckers());
    	System.out.println("Bear Off Checkers:" + getNumCheckersBearOff());
        
        int[][] currentCheckerLayout = board.get();
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
            // some sort of maths that adds all the probabilitys and weigths together for each play;
            // function calls to features should be made here
            probability.add(1.0);
        }
        
        int choice = 0;
        Double highestProbability = 0.0;
        
        for(int i = 0;i < probability.size();i++)
            if(probability.get(i) > highestProbability) {
                highestProbability = probability.get(i);
                choice = i + 1;
            }
            
        // Add your code here
        return "" + choice + "";

//        return "1";
    }
    
    //TODO test
    public int getPipCountDifference() {
    	int pipCountP0 = 0;
    	int pipCountP1 = 1;
    	for(int i = 1; i <= 25; i++) {
    		pipCountP0 += board.getNumCheckers(me.getId(), i) * i;
    		pipCountP1 += board.getNumCheckers(opponent.getId(), i) * i;
    	}
    	
    	return pipCountP0 - pipCountP1;
    }
    
    //TODO test
    public int getBlockBlotDifference() {
    	int block = 0;
    	int blot = 1;
    	for(int i = 1; i < 25; i++) {
    		if(board.getNumCheckers(me.getId(), i) >= 2)
    			block ++;
    		else if(board.getNumCheckers(opponent.getId(), i) == 1) // should be me.getId ?
    			blot++;
    	}
    	
    	return block - blot;
    }
    
    //TODO test
    // weights added, most important points in descending order: 6, 5, 4, 3, 2, 1
    public int getNumHomeBoardBlocks() {
    	int homeBlocks = 0;
    	for(int i = 1; i <= 6; i++) {
    		if(board.getNumCheckers(me.getId(), i) >= 2)
    			homeBlocks += i;
    	}
    
    	return homeBlocks;
    }
    
    public int getEscapedCheckers() {
    	int escapedCheckers = 0;
    	for(int i = getLastOpponentChecker(); i > 0; i--) {
    		escapedCheckers += board.getNumCheckers(me.getId(), i);
    	}
    	
    	return escapedCheckers;
    }
    
    public int getLastOpponentChecker() {
    	int pip = 0;
    	for(int i = 0; i < 25; i++) {
    		if(board.getNumCheckers(opponent.getId(), i) >= 1)
    			pip = i;
    	}
    	pip = 25 - pip;
    	
    	return pip;
    }
    
    public int getNumHomeCheckers()
    {
    	int homeCheckers = 0;
    	for(int i = 1; i <= 6; i++) 
    	{
    		if(board.getNumCheckers(me.getId(),i)>= 1)
    		{
    			homeCheckers += board.getNumCheckers(me.getId(),i);
    		}
    	}
    	
    	return homeCheckers;
    }
    
    // weighted by how advanced the anchors are on the board
    public int getNumAnchors() {
    	int numAnchors = 0;
    	for(int i = 19; i <= 24; i++) {
    		if(board.getNumCheckers(me.getId(),i) >= 2)
    			numAnchors += 25 - i;
    	}
    	
    	return numAnchors;
    }
    
    public int getNumCheckersBearOff()
    {
    	int checkersBearOff = 0;
    	
    	if(board.getNumCheckers(me.getId(),0)>= 1)
		{
			checkersBearOff += board.getNumCheckers(me.getId(),0);
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
                    primeScore += primeCounter;
                    primeCounter = 0;
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
    	
    	if(cube.getOwnerId() == me.getId() && (match.getLength() - me.getScore() <= 2 && match.getLength() - opponent.getScore() <= 2)) 
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
