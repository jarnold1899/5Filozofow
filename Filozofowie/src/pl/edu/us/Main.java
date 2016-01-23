package pl.edu.us;

import java.util.ArrayList;

public class Main {
	private static final int liczbaFilozofow = 5;
	static final int czas = 30;
	private static ArrayList<Widelec> widelce = new ArrayList<Widelec>();
	static ArrayList<Filozof> filozofowie = new ArrayList<Filozof>();
	private static boolean blokada;

	public static void main(String[] args) {
		for (int i = 0; i < getLiczbafilozofow(); i++)
			getWidelce().add(new Widelec());
		for (int i = 0; i < getLiczbafilozofow(); i++)
			filozofowie.add(new Filozof());
		for (Filozof p : filozofowie)
			new Thread(p).start();
		long koniec = System.currentTimeMillis() + (czas * 1000);

		do { 
			blokada = true;
			StringBuilder sb = new StringBuilder("|");

			for (Filozof p : filozofowie) {
				sb.append(p.getStatus().toString());
				sb.append("|");
				if (p.getStatus() != Status.Wez)
						blokada = false;
			} 
			
			if (blokada) {
				for (Filozof p : filozofowie) {
					p.setStatus(Status.Mys);
				} 
				for (Widelec w : widelce) {
					w.getPosiadacz().set(Widelec.STOL);
				}
			}

			sb.append("     |");

			for (Widelec f : getWidelce()) {
				int holder = f.getPosiadacz().get();
				sb.append(holder == -1 ? "   " : String.format("P%02d", holder));
				sb.append("|");
			}

			System.out.println(sb.toString());
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
			}
		} while (System.currentTimeMillis() < koniec);

		for (Filozof p : filozofowie)
			p.koniec.set(true);
		for (Filozof p : filozofowie)
			System.out.printf("P%02d: jad³ %,d razy\n", p.getId(), p.getIloscPosilkow());
	}

	static ArrayList<Widelec> getWidelce() {
		return widelce;
	}

	static void setWidelce(ArrayList<Widelec> widelce) {
		Main.widelce = widelce;
	}

	static int getLiczbafilozofow() {
		return liczbaFilozofow;
	}
}