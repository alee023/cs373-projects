public class Transition {
	String read ; // symbol that was read 
	String write ; // symbol to write
	String move ; // l/s/r
	int state ; // state to move to

	public Transition( String r, String w, String m, int newState ) {
		read = r ;
		write = w ;
		move = m ;
		state = newState ;
	}
}