package Application;
import example_basis.MyRunnable;
import example_basis.MyThread;

public class Program {
	public static void main(String[] args) {
		MyThread firstOne = new MyThread();
		
		MyRunnable runnableOne = new MyRunnable();
		
		Thread secondOne = new Thread(runnableOne);
		
		firstOne.setPriority(10);
		secondOne.start();
		firstOne.start();

    }

}
