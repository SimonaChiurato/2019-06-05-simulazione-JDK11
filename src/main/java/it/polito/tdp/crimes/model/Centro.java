package it.polito.tdp.crimes.model;

public class Centro {
	Integer distretto;
	
	double lon;
	double lat;
	
	
	
	public Centro(Integer distretto, double lon, double lat) {
		super();
		this.distretto = distretto;
		this.lon = lon;
		this.lat = lat;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distretto == null) ? 0 : distretto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Centro other = (Centro) obj;
		if (distretto == null) {
			if (other.distretto != null)
				return false;
		} else if (!distretto.equals(other.distretto))
			return false;
		return true;
	}
	public Integer getDistretto() {
		return distretto;
	}
	public void setDistretto(Integer distretto) {
		this.distretto = distretto;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	

}
