package Taskeren.GameOfLife;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class World {
	
	boolean init = false;
	
	// Output
	protected PrintWriter out;
	
	// World Container
	private Point[][] WORLD;
	private Point[][] UPDATER;
	
	// World Limit
	private int lx;
	private int ly;
	
	int tick = 20;
	
	public World() {}
	
	/**
	 * Initialize the world before running
	 * @param lx The world X limit
	 * @param ly The world Y limit
	 * */
	public void init(int lx, int ly) {
		this.lx = lx >= 0 ? lx : 0;
		this.ly = ly >= 0 ? ly : 0;
		// init World Container
		WORLD = new Point[this.lx][this.ly];
		UPDATER = new Point[this.lx][this.ly];
		// init PrintWriter
		try {
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("d:/gol.data.txt"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int y=0; y!=this.ly; y++) {
			for(int x=0; x!=this.lx; x++) {
				WORLD[x][y] = new Point();
			}
		}
		if(GameOfLife.RANDOM) generate();
		init = true;
	}
	
	public void loop() throws InterruptedException {
		int delay = tick == 0 ? 1000 : 1000 / tick;
		int time = 0;
		while(true) {
			time++;
			String re = "=============>µÚ"+time+"´Îµü´ú<==============\r\n" + this;
			System.out.println(re);
			out.println(re);
			this.update();
			Thread.sleep(delay);
		}
	}
	
	public void update() {
		UPDATER = WORLD;
		for(int y=0; y!=WORLD.length; y++) {
			for(int x=0; x!=WORLD[y].length; x++) {
				Point[] reachable = getReachablePoint(x, y);
				int full = 0;
				for(Point p : reachable) {
					if(p.isFull()) full++;
				}
				if(WORLD[x][y].isFull()) {
					if(full > 4) UPDATER[x][y].setClear();
					if(full <=2) UPDATER[x][y].setClear();
				} else {
					if(full == 3) UPDATER[x][y].setFull();
				}
			}
		}
		WORLD = UPDATER;
	}
	
	public Point getPoint(int x, int y) {
		if(y > WORLD.length - 1 || y < 0) return Point.CLEAR;
		if(x > WORLD[0].length - 1 || x < 0) return Point.CLEAR;
		return WORLD[x][y];
	}
	
	Point[] getReachablePoint(int x, int y) {
		Point[] re = {
			getPoint(x-1, y-1), getPoint(x, y-1), getPoint(x+1, y-1),
			getPoint(x-1,  y ), getPoint(x,  y ), getPoint(x+1,  y ),
			getPoint(x-1, y+1), getPoint(x, y+1), getPoint(x+1, y+1)
		};
		return re;
	}
	
	/**
	 * Set point to <em>full</em>
	 * */
	public void setFull(int x, int y) {
		this.getPoint(x, y).setFull();
	}
	
	/**
	 * @param pos IntArray of arrays including x and y of the point
	 * */
	public void setFull(int[][] pos) {
		for(int[] s : pos) {
			if(s.length==2) this.setFull(s[0], s[1]);
			if(s.length!=2) System.err.println("Wrong Agreement.");
		}
	}
	
	/**
	 * @return Is point full. If the point isn't found, returns <em>false</em>
	 * */
	public boolean isFull(int x, int y) {
		return this.getPoint(x, y).isFull();
	}
	
	// Auto generate 100 points
	public void generate() {
		this.generate(100);
	}
	
	/**
	 * @param i How many points do you want to generate.
	 * */
	public void generate(int i) {
		Random seed = new Random();
		Random rx = new Random(seed.nextInt());
		Random ry = new Random(rx.nextInt());
		int[][] pos = new int[i][2];
		for(int k=0; k<i; k++) {
			pos[k][0] = rx.nextInt(lx);
			pos[k][1] = ry.nextInt(ly);
		}
		this.setFull(pos);
	}
	
	@Override
	public String toString() {
		String re = "";
		for(int y=0; y!=WORLD.length; y++) {
			for(int x=0; x!=WORLD[y].length; x++) {
				re = re + WORLD[x][y].toString();
			}
			re = re + "\r\n";
		}
		return re;
	}
	
}
