package launcher;

import utilities.EntombedProcedure;

public class Launcher {
	// 10011010011101000110000110101111
	// 11100000011000011101000110111001
	// 10111011000101110100101011111001
	// 10101110010101010100011011001011
	
	// 111200221111200011120002012200 -> original
	// 111200221111200011220002012200 -> fonctionnel
	private static int lines = 50;
	private static int columns = 50;
	private static int sleep = 0;
	private static String seed = "";
	private static boolean revert = false;

	public static void main(String[] args) {
		String message = parseArgs(args);

		if(message != null) {
			System.out.println(message);
			return;
		} else {
			EntombedProcedure ep = new EntombedProcedure(lines, columns, sleep, seed);
			if(!revert) {				
				ep.printCharList();
			} else {
				ep.printCharListRevert();
			}
			ep.printRandomSelector();
		}
	}

	private static String parseArgs(String[] args) {
		String message = null;
		for(int i = 0; i < args.length; i++) {
			switch(args[i]) {
			case "-l" :
				lines =  Integer.parseInt(args[i+1]);
				break;
			case "-c":
				columns = Integer.parseInt(args[i+1]);
				break;
			case "-sl":
				sleep = Integer.parseInt(args[i+1]);
				break;
			case "-sd":
				seed = args[i+1];
				if(seed.length() != 32) {
					message = "The seed argument must take 32 bits";
				} else if(seed.replaceAll("[0-9]", "").length() > 0) {
					message = "The seed argument must be a binary chain (only numeric)";
				}
				break;
			case "-r":
				revert = Boolean.parseBoolean(args[i+1]);
				break;
			case "-h":
				message = String.join("\r\n",
						"",
						"Available arguments: ",
						"",
						"-l <lines, 50>\t: Number of lines to generate",
						"-c <columns, 50>\t: Number of columns per line",
						"-r <revert, False>\t: Boolean that indicates if the banner must be printed in mirror mode",
						"-sl <sleep, 0>\t: Time between two lines in mili-seconds. If < 20 or > 200, no sleep",
						"-sd <seed [32 bits]>\t: seed to generate the banner (string of 32 characters, 0 or 1). If not set, seed will be randomly generated.",
						"-h*\t: Help");
				break;
			}
		}
		return message;
	}
}
