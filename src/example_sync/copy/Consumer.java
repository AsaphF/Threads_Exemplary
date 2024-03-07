package example_sync.copy;


public class Consumer implements Runnable {
	 Producer producer;
	 String name;
	 
	 public Consumer( Producer producer, String name ) {
		 this.producer = producer;
		 this.name = name;
	 }
	 
	 public void run( ) {
		 while ( true ) {
			 String message = producer.getMessage( );
			 System.out.println("Consumer " + name);
			 System.out.println("Got message: " + message);
			 try {
				 Thread.sleep( 2000 );
			 } catch ( InterruptedException e ) { }
		 }
	 }
	}
