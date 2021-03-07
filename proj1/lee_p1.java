import java.io.BufferedReader ;
import java.io.FileReader ;
import java.util.Arrays ;
import java.util.ArrayList ;

public class lee_p1 {
	static ArrayList<State> states = new ArrayList<State>() ;
	static int rwPter = 0 ; // reader-writer pointer
	static int nextPter = 0 ; // pointer for the next step
	static int stepsTaken = 0 ; // counter, will be checked against the argument
	static int totalSteps ; // argument stored here
	static ArrayList<String> input ; // string to be read
	static int currStateNum ;

	// if the reader reads a state in the txt file
	public static void ifState( String[] line ) {
		int stateNum = Integer.parseInt( line[ 1 ]) ;
		int typeState ; // 0 = start; 1 = normal; 2 = accept; 3 = reject
		
		if( line.length == 3 ) {
			if( line[ 2 ].equals( "start" )) {
				typeState = 0 ;
				currStateNum = stateNum ;
			}
			else if( line[ 2 ].equals( "accept" )) {
				typeState = 2 ;
			}
			else { // assume reject
				typeState = 3 ;
			}
		}
		else { // there's no start/accept/reject specified, so the state is assumed to be regular
			typeState = 1 ;
		}
		// add the state
		states.add( new State( stateNum, typeState )) ;
	}

	// if the reader reads a transition in the txt file
	public static void ifTransition( String[] line ) {
		int currState = Integer.parseInt( line[ 1 ]) ; // start pt of transition
		int newState = Integer.parseInt( line[ 3 ]) ; // end pt of trans
		String readSymb = line[ 2 ] ;
		String writeSymb = line[ 4 ] ;
		String move = line[ 5 ] ; // l/s/r

		for( int i = 0; i < states.size(); i++ ) {
			if(( states.get( i )).stateNum == currState ) {
				// add transition to the state that the trans starts from
				( states.get( i )).addTrans( new Transition( readSymb, writeSymb, move, newState )) ;
			}
		}
	}

	public static int getIndexOfState( int state_num ) {
		int i ;
		for( i = 0; i < states.size(); i++ ) {
			if( state_num == ( states.get( i )).stateNum ) {
				break ;
			}
		}

		return i ;
	}

	public static String answer() { // where the actual work is to be done
		boolean reachedEnd = false ;  // set to true if reached accept/reject state
		String symbolRead ;
		int currStateIndex = 0 ;
		while( stepsTaken < totalSteps && reachedEnd == false ) {
			stepsTaken++ ;

			currStateIndex = getIndexOfState( currStateNum ) ;

			if( rwPter < input.size()) { // not past the end of the input str
				symbolRead = input.get( rwPter ) ;
			}
			else { // past the end of the input string
				symbolRead = "_" ;
			}


			ArrayList<Transition> transOfCurrentState = ( states.get( currStateIndex )).transitions ;
			// find the right transition for the symbol that was read, store trans details in x
			Transition x = transOfCurrentState.get( 0 ) ;
			for( int j = 0; j < transOfCurrentState.size(); j++ ) {
				if((( transOfCurrentState.get( j )).read ).equals( symbolRead )) {
					x = transOfCurrentState.get( j ) ;
					break ;
				}
			}

			if( rwPter < input.size()) {
				input.set( rwPter, x.write ) ; // replace w/ intended symbol
			}
			else {
				input.add( x.write ) ; // add intended symbol to the string (arraylist)
			}

			if( x.move.equals( "R" )) {
				nextPter += 1 ;
			}
			else if( x.move.equals( "L" )) {
				nextPter = rwPter - 1 ;
			}
			else { // S-move
				nextPter = rwPter ;
			}

			rwPter = nextPter ;
			currStateNum = x.state ;
			currStateIndex = getIndexOfState( currStateNum ) ;
			// if the new state is an accept or reject state
			if(( states.get( currStateIndex )).stateType == 2  || ( states.get( currStateIndex )).stateType == 3 ) {
				reachedEnd = true ;
			}
		}

		StringBuilder retStr = new StringBuilder() ;
		for( int i = rwPter; i < input.size(); i++ ) {
			retStr.append( input.get( i )) ;
		}
		if(( states.get( currStateIndex )).stateType == 1 ) { // means it stopped midway
			retStr.append( " quit\n" ) ;
		}
		else if(( states.get( currStateIndex )).stateType == 2 ) {
			retStr.append( " accept\n" ) ;
		}
		else { // reject
			retStr.append( " reject\n" ) ;
		}

		return( retStr.toString()) ;
	}

	public static void main( String[] args ) {
		// splits input into array of letters, then turns into arraylist
		input = new ArrayList<String>( Arrays.asList( args[ 1 ].split( "" ))) ;
		totalSteps = Integer.parseInt( args[ 2 ]) ;

		try {
			BufferedReader buf = new BufferedReader( new FileReader( args[ 0 ])) ;
			String line = null ;
			String[] wordsArr ;

			while( true ) {
				line = buf.readLine() ;
				if( line == null ) { 
					break ;
				}
				else {
					wordsArr = line.split( "\t" ) ;
					if( wordsArr[ 0 ].equals( "state" )) {
						ifState( wordsArr ) ;
					}
					else { // transition
						ifTransition( wordsArr ) ;
					}
				}
			}

			buf.close() ;
		}
		catch( Exception e ) {
			e.printStackTrace() ;
		}

		System.out.println( answer()) ;
	}
}