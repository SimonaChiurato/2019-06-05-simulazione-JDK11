/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import it.polito.tdp.crimes.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	this.txtResult.clear();
    	int anno= this.boxAnno.getValue();
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText("Grafo creato con #vertici: "+this.model.vertici().size()+" #archi: "+this.model.archi().size()+"\n");
    	List<Integer> vertici = new ArrayList<>(this.model.vertici());
    	Collections.sort(vertici);
    	for(Integer i: vertici ) {
    		this.txtResult.appendText("Vicini di "+i+":\n");
    		for(Vicino v: this.model.getVicini(i)) {
    			this.txtResult.appendText(v.getDistretto()+" "+v.getDistanza()+"\n");
    		}
    	}
    this.boxMese.getItems().addAll(this.model.listAllMonth());
    this.boxGiorno.getItems().addAll(this.model.listAllDay());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	int anno= this.boxAnno.getValue();
    	Integer giorno= this.boxGiorno.getValue();
    	Integer mese= this.boxMese.getValue();
    	String input= this.txtN.getText();
    	
    	if(input.matches("[0-9]+") && Integer.parseInt(input)>0 && Integer.parseInt(input)<11) {
    		Integer N= Integer.parseInt(input);
    		this.model.simula(anno, mese, giorno, N);
    	}else {
    		this.txtResult.appendText("Devi inserire un numero compreso tra 1 e 10\n");
    	}
    	this.txtResult.appendText("MalGestiti: "+this.model.malGestiti());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.listAllYears());
    }
}
