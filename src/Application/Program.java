package Application;

import example_sync.copy.Consumer;
import example_sync.copy.Producer;

public class Program {
	public static void main(String[] args) {
		Producer producer = new Producer( );
	 	new Thread( producer ).start( );
	 	
	 	Consumer consumer1= new Consumer( producer, "Primeiro" );
	 	new Thread( consumer1 ).start( );
	 	
	 	Consumer consumer2 = new Consumer( producer, "Segundo" );
	 	new Thread( consumer2 ).start( );

    }

}
