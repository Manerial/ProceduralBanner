package utilities;

import java.util.concurrent.TimeUnit;

public class EntombedProcedure {
	private Boolean[][] charList;
	private int[] randomSelector = new int[32];
	private int sleep;
	private static final char BLACK_CHAR = (char)219;
	
	// 0 = false = pas de mur
	// 1 = true = mur

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
			// Current cell = X
			
			// sTile = 
			//   2 3 4
			// 0 1 X
			
			Boolean[] sTile = new Boolean[5];
			sTile[0] = charList[line][column - 2];
			sTile[1] = charList[line][column - 1];
			sTile[2] = charList[line - 1][column - 1];
			sTile[3] = charList[line - 1][column];
			sTile[4] = charList[line - 1][column + 1];
			int index = getSTileSelectorIndex(sTile);
			switch(randomSelector[index]) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				return Math.random() > 0.5;
			}
		} catch (Exception e) {
			if(column == 0) {
				return true;
			} else if (column == 1 && line > 0) {
				return false;
			} else {
				return Math.random() > 0.5;
			}
		}
	}

	public int getSTileSelectorIndex(Boolean[] sTile) {
		int index = 0;
		for(int i = 0; i < sTile.length; i++) {
			boolean curentCellIsWall = sTile[i];
			index += (curentCellIsWall) ? Math.pow(2, 4-i) : 0;
		}
		return index;
	}

	public void printCharList() {
		for(int line = 0; line < charList.length; line++) {
			for(int column = 0; column < charList[line].length; column++) {
				printWall(line, column);
			}
			System.out.println(line);
			sleep();
		}
	}

	public void printCharListRevert() {
		for(int line = 0; line < charList.length; line++) {
			for(int column = 0; column < charList[line].length; column++) {
				printWall(line, column);
			}
			for(int column = charList[line].length - 1; column >= 0; column--) {
				printWall(line, column);
			}
			System.out.println(line);
			sleep();
		}
		for(int column = 0; column < charList[0].length * 2; column++) {
			System.out.print(BLACK_CHAR);
		}
	}

	private void printWall(int line, int column) {
		if(column == 1) {
			System.out.print(BLACK_CHAR);
		} else {			
			System.out.print((charList[line][column]) ? BLACK_CHAR : " ");
		}
	}

	private void sleep() {
		if(sleep >= 20 && sleep <= 200) {
			try {
				TimeUnit.MILLISECONDS.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void printRandomSelector() {
		System.out.println();
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
			if(rand < 0.4) {
				randomSelector[line] = 0;
			} else if (rand < 0.8) {
				randomSelector[line] = 1;
			} else {
				randomSelector[line] = 2;
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