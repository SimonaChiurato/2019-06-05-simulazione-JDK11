package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {

	int anno;
	int mese;
	 int giorno;
	 int N;
	 
	 int DistrettoCentrale;
	 Map<Integer, Integer> disAgenti;
	 PriorityQueue<Evento> queue;
	 
	 SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo;

	 EventsDao dao;
	 int malGestiti;
	private Model model;
	public Simulator(int anno, int mese, int giorno, int n, int distrettoCentrale,
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo, Model model) {
		super();
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		N = n;
		DistrettoCentrale = distrettoCentrale;
		this.grafo = grafo;
		dao= new EventsDao();
		this.model= model;

		this.malGestiti=0;
		}
	
	public void init() {
		this.queue= new PriorityQueue<Evento>();
		this.disAgenti= new HashMap<>();
		List<Event> crimini = this.dao.listAllEvents(anno, mese, giorno);
		
		for(Integer d: this.grafo.vertexSet()) {
			if(d.equals(DistrettoCentrale)) {
				this.disAgenti.put(d, N);
			}else {
				this.disAgenti.put(d, 0);
			}
		}
		for(Event c: crimini) {
			queue.add(new Evento(c, c.getReported_date(),EventType.CRIMINE));
		}
		
		
	}
public void run() {
			Evento e = null;
			while(!queue.isEmpty()) {
				e= queue.poll();
			}
			
			switch(e.getTipo()) {
			case CRIMINE:
				int partenza=-1;
				double distanza=0.0;
				if(this.disAgenti.get(e.getCrimine().getDistrict_id())>0){
					
					partenza= e.getCrimine().getDistrict_id();
					this.disAgenti.put(partenza, this.disAgenti.get(partenza)-1);
					distanza=0.0;
				}else {
				List<Vicino> distrettoVicino= this.model.getVicini(e.getCrimine().getDistrict_id());
				for(Vicino v: distrettoVicino) {
					if(this.disAgenti.get(v.getDistretto())>0) {
						partenza= v.getDistretto();
						this.disAgenti.put(partenza, this.disAgenti.get(partenza)-1);
						distanza= this.grafo.getEdgeWeight(grafo.getEdge(v.getDistretto(), e.getCrimine().getDistrict_id()));
					}
				}
				}
				if(partenza==-1 || distanza>15) {
					this.malGestiti++;
				}
				queue.add(new Evento(e.getCrimine(), e.getData().plusMinutes((long) distanza),EventType.ARRIVA_AGENTE));
				
				break;
			case ARRIVA_AGENTE:
				long ore;
				if(e.getCrimine().getOffense_category_id()=="all_other_crimes") {
					Random r = null;
					if(r.nextDouble()>0.5) {
						ore=2;
					}else {
						ore=1;
					}
				}else {
					ore=2;
				}
				
				queue.add(new Evento(e.getCrimine(), e.getData().plusHours(ore),EventType.ARRIVA_AGENTE));
				break;
			
			case GESTITO:
				this.disAgenti.put(e.getCrimine().getDistrict_id(), this.disAgenti.get(e.getCrimine().getDistrict_id())+1);
				break;
			
			
			}
		}

public int malGestiti() {
	return this.malGestiti;
}





}
