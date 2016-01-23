package pl.edu.us;

import java.util.concurrent.atomic.AtomicInteger;

class Widelec {
	public static final int STOL = -1;
	private static int iloscWidelcy = 0;
	private int id;
	private AtomicInteger posiadacz = new AtomicInteger(STOL);

	Widelec() {
		id = iloscWidelcy++;
	}

	public AtomicInteger getPosiadacz() {
		return posiadacz;
	}

	public void setPosiadacz(AtomicInteger holder) {
		this.posiadacz = holder;
	}

	public int getId() {
		return id;
	}

}