package pl.edu.us;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Filozof implements Runnable {
	static final int maxWaitMs = 100;
	static AtomicInteger token = new AtomicInteger(0);
	static int iloscFilozofow = 0;
	static Random rand = new Random();
	AtomicBoolean koniec = new AtomicBoolean(false);
	private int id;

	private Status status = Status.Wez;
	private Widelec lewy;
	private Widelec prawy;
	private int iloscPosilkow = 0;

	Filozof() {
		id = iloscFilozofow++;
		lewy = Main.getWidelce().get(id);
		prawy = Main.getWidelce().get((id + 1) % Main.getLiczbafilozofow());
	}

	void sleep() {
		try {
			Thread.sleep(rand.nextInt(maxWaitMs));
		} catch (InterruptedException ex) {
		}
	}

	void czekajNaWidelec(Widelec fork) {
		do {
			if (fork.getPosiadacz().get() == Widelec.STOL) {
				fork.getPosiadacz().set(id);
				return;
			} else { // ktos ma widelec
				sleep(); // czekaj na kolejke
			}
		} while (true);
	}

	public void run() {
		do {
			if (getStatus() == Status.Mys) {
				setStatus(Status.Wez);
			} else {
				if (token.get() == id) {
					czekajNaWidelec(lewy);
					czekajNaWidelec(prawy);
					token.set((id + 2) % Main.getLiczbafilozofow());
					setStatus(Status.Jem);
					iloscPosilkow++;
					sleep(); // jedz
					lewy.getPosiadacz().set(Widelec.STOL);
					prawy.getPosiadacz().set(Widelec.STOL);
					setStatus(Status.Mys);
					sleep(); // myœl
				} else {
					sleep();
				}
			}
		} while (!koniec.get());
	}

	public int getIloscPosilkow() {
		return iloscPosilkow;
	}

	public void setIloscPosilkow(int iloscPosilkow) {
		this.iloscPosilkow = iloscPosilkow;
	}

	public int getId() {
		return id;
	}

	Status getStatus() {
		return status;
	}

	void setStatus(Status status) {
		this.status = status;
	}
}
