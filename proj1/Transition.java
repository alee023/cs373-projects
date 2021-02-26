public class Transition {
	String read ;
	String write ;
	String move ;
	int state ;

	public Transition( String r, String w, String m, int newState ) {
		read = r ;
		write = w ;
		move = m ;
		state = newState ;
	}
}