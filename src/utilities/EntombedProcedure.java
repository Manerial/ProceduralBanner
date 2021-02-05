package utilities;

import java.util.concurrent.TimeUnit;

public class EntombedProcedure {
	private Boolean[][] charList;
	private int[] randomSelector = new int[32];
	private int sleep;

	public EntombedProcedure(int lines, int columns, int sleep, String seed) {
		this.sleep = sleep;
		if(seed != null && seed.length() == 32) {
			setRandomSelector(seed);
		} else {
			generateRandomSelector();
		}
		
		generateCharList(lines, columns);
	}

	public boolean getNextCell(int line, int column) {
		try {
			Boolean[] booleanList = new Boolean[5];
			booleanList[0] = charList[line][column - 2];
			booleanList[1] = charList[line][column - 1];
			booleanList[2] = charList[line - 1][column - 1];
			booleanList[3] = charList[line - 1][column];
			booleanList[4] = charList[line - 1][column + 1];
			int boolList = boolListToInt(booleanList);
			switch(randomSelector[boolList]) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				return Math.random() > 0.5;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public int boolListToInt(Boolean[] booleanList) {
		int boolInt = 0;
		for(int i = 0; i < booleanList.length; i++) {
			boolInt += (booleanList[4-i]) ? Math.pow(2, i) : 0;
		}
		return boolInt;
	}

	public void printCharList() {
		for(int line = 0; line < charList.length; line++) {
			for(int column = 0; column < charList[line].length - 1; column++) {
				System.out.print((charList[line][column]) ? " " : (char)219);
			}
			System.out.println(line);
			sleep();
		}
	}

	public void printCharListRevert() {
		for(int line = 0; line < charList.length; line++) {
			for(int column = 0; column < charList[line].length - 1; column++) {
				System.out.print((charList[line][column]) ? " " : (char)219);
			}
			for(int column = charList[line].length - 2; column >= 0; column--) {
				System.out.print((charList[line][column]) ? " " : (char)219);
			}
			System.out.println(line);
			sleep();
		}
		for(int column = 0; column < charList[0].length * 2 - 2; column++) {
			System.out.print((char)219);
		}
	}

	private void sleep() {
		if(sleep > 20) {
			try {
				TimeUnit.MILLISECONDS.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void printRandomSelector() {
		for(int line = 0; line < randomSelector.length; line++) {
			System.out.print(randomSelector[line]);
		}
	}

	private void setRandomSelector(String string) {
		for(int charPos = 0; charPos < 32; charPos++) {
			randomSelector[charPos] = Integer.parseInt("" + string.charAt(charPos));
		}
	}

	private void generateRandomSelector() {
		for(int line = 0; line < randomSelector.length; line++) {
			double rand = Math.random();
			// Si premier bit = 1 : ecran blanc
			if(rand < 0.5 && line > 0) {
				randomSelector[line] = 0;
			} else if (rand < 0.8) {
				randomSelector[line] = 1;
			} else {
				randomSelector[line] = 1;
			}
		}
	}

	private void generateCharList(int lines, int columns) {
		charList = new Boolean[lines][columns];
		for(int line = 0; line < lines; line++) {
			for(int column = 0; column < columns; column++) {
				charList[line][column] = getNextCell(line, column);
			}
		}
	}
}