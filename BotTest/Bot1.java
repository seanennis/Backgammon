
public class Bot1 implements BotAPI {

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

    Bot1 (PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "Bot1"; // must match the class name
    }

    public String getCommand(Plays possiblePlays) {
        // Add your code here
        int playNumber = 1 + (int) (Math.random() * possiblePlays.number());
        return Integer.toString(playNumber);
    }

    public String getDoubleDecision() {
        // Add your code here
        return "n";
    }

	@Override
	public int getPipCountDifference() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockBlotDifference() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumHomeBoardBlocks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEscapedCheckers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastOpponentChecker() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumHomeCheckers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumAnchors() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumCheckersBearOff() {
		// TODO Auto-generated method stub
		return 0;
	}

}
