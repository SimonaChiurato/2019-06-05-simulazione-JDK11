package it.polito.tdp.crimes.model;

public class Vicino implements Comparable<Vicino> {

	private int distretto;
	private double distanza;
	public int getDistretto() {
		return distretto;
	}
	public void setDistretto(int distretto) {
		this.distretto = distretto;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	public Vicino(int distretto, double distanza) {
		super();
		this.distretto = distretto;
		this.distanza = distanza;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return Double.compare(this.distanza, o.distanza);
	}
	
	
}
