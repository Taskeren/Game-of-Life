package Taskeren.GameOfLife;

public class GameOfLife {
	
	public static final boolean RANDOM = true;
	
	public static void main(String[] args) {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					World w = new World();
					w.init(16, 16);
					w.loop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(run).start();
	}
	
}
