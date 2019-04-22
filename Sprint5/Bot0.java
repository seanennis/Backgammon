
public class Bot0 implements BotAPI {

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

    Bot0(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "Dave"; // must match the class name
    }

    public String getCommand(Plays possiblePlays) {
        // Add your code here
    	System.out.println("Pip Dif: " + getPipCountDifference());
    	System.out.println("Block Blot Dif: " + getBlockBlotDifference());
    	System.out.println("Home Board Blocks: " + getNumHomeBoardBlocks());
    	System.out.println("Home Board Checkers:" + getNumHomeCheckers());
    	System.out.println("Bear Off Checkers:" + getNumCheckersBearOff());
    	System.out.println("Bar Off Checkers:" + getNumCheckersBarOff());
    	
        return "1";
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
    		else if(board.getNumCheckers(opponent.getId(), i) == 1)
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
    
    public int getNumCheckersBearOff()
    {
    	int checkersBearOff = 0;
    	
    	if(board.getNumCheckers(me.getId(),0)>= 1)
		{
			checkersBearOff += board.getNumCheckers(me.getId(),0);
		}
    	
    	return checkersBearOff;
    }
    
    public int getNumCheckersBarOff()
    {
    	int checkersBarOff = 0;
    	
    	if(board.getNumCheckers(me.getId(),25)>= 1)
		{
			checkersBarOff += board.getNumCheckers(me.getId(),25);
		}
    	
    	return checkersBarOff;
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
