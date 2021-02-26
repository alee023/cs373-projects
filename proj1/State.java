import java.util.ArrayList ;

public class State {
	int stateNum ;
	int stateType ;
	ArrayList<Transition> transitions = new ArrayList<Transition>() ;

	public State( int num, int type ) {
		stateNum = num ;
		stateType = type ;
	}

	/*
	public int getNum() {
		return stateNum ;
	}

	public int getType() {
		return stateType ;
	}
	*/
	
	public void addTrans( Transition trans ) {
		transitions.add( trans ) ;
	}
}