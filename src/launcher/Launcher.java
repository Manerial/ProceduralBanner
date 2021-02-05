package launcher;

import utilities.EntombedProcedure;

public class Launcher {
// 10011010011101000110000110101111
// 11100000011000011101000110111001
// 10111011000101110100101011111001
	public static void main(String[] args) {
		int lines = 50;
		int columns = 50;
		String seed = "";
		int sleep = 0;
		try {
			lines = Integer.parseInt(args[0]);
			columns = Integer.parseInt(args[1]);
			sleep = Integer.parseInt(args[2]);
			seed = args[3];
		} catch(Exception e) {}
		EntombedProcedure ep = new EntombedProcedure(lines, columns, sleep, seed);
		ep.printCharList();
		System.out.println();
		ep.printRandomSelector();
	}
}
