import java.io.BufferedReader ;
import java.io.FileReader ;
import java.util.ArrayList ;

public class lee_p1 {
	static ArrayList<State> states = new ArrayList<State>() ;

	public static void ifState( String[] line ) {
		int stateNum = Integer.parseInt( line[ 1 ]) ;
		int typeState ; // 0 = start; 1 = normal; 2 = accept; 3 = reject
		
		if( line.length == 3 ) {
			if( line[ 2 ].equals( "start" )) {
				typeState = 0 ;
			}
			else if( line[ 2 ].equals( "accept" )) {
				typeState = 2 ;
			}
			else { // assume reject
				typeState = 3 ;
			}
		}
		else { // theres no start/accept/reject specified, so the state is assumed to be regular
			typeState = 1 ;
		}

		states.add( new State( stateNum, typeState )) ;
	}

	public static void ifTransition( String[] line ) {
		int currState = Integer.parseInt( line[ 1 ]) ;
		int newState = Integer.parseInt( line[ 3 ]) ;
		String readSymb = line[ 2 ] ;
		String writeSymb = line[ 4 ] ;
		String move = line[ 5 ] ;

		for( int i = 0; i < states.size(); i++ ) {
			if(( states.get( i )).stateNum == currState ) {
				( states.get( i )).addTrans( new Transition( readSymb, writeSymb, move, newState )) ;
			}
		}
	}

	public static void main( String[] args ) {
		System.out.println( args[ 0 ]) ;
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

		for( int i = 0; i < states.size(); i++ ) {
			System.out.println(( states.get( i )).stateNum ) ;
		}
	}
}