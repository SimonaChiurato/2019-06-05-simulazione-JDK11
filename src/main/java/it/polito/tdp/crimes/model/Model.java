package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo;
	Simulator sim;
	public Model() {
		this.dao= new EventsDao();
	}
	
	public List<Integer> listAllYears(){
		return this.dao.listAllYears();
	}
	
	public void creaGrafo (int anno) {
		this.grafo= new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.dao.listAllDistretti());
		for(Centro c1: this.dao.listAllCentri(anno)) {
			for(Centro c2: this.dao.listAllCentri(anno)) {
				if(!c1.equals(c2)) {
					Double distanza= LatLngTool.distance(new LatLng(c1.getLat(),c1.getLon()), 
							new LatLng(c2.getLat(),c2.getLon()), LengthUnit.KILOMETER);
				Graphs.addEdgeWithVertices(grafo, c1.getDistretto(), c2.getDistretto(), distanza);
				}
				
			}
		}
	}
	
	public  Set<Integer> vertici() {
		return this.grafo.vertexSet();
		}
		public  Set<DefaultWeightedEdge> archi() {
		return this.grafo.edgeSet();
		}
	

	public List<Vicino> getVicini(Integer distretto){
		List<Vicino> result= new ArrayList<>();
		
		List<Integer> vicini= Graphs.neighborListOf(grafo, distretto);
		
		for(Integer i: vicini) {
			result.add(new Vicino(i, grafo.getEdgeWeight(grafo.getEdge(distretto, i))));
		}
		Collections.sort(result);
		return result;
	}
	public List<Integer> listAllMonth(){
		return this.dao.listAllMonth();
	}
	public List<Integer> listAllDay(){
		return this.dao.listAllDays();
	}
	
	public void simula(int anno, int mese, int giorno, int N) {
		sim= new Simulator(anno, mese, giorno, N, this.dao.distrettoMinoreCriminalita(anno), grafo, this);
		sim.init();
		sim.run();
	}
	public int malGestiti() {
		return this.sim.malGestiti();
	}
}
