package com.omegapoint.opendatagateway.modell;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "vat", type = "xldata")
public class Vatmark {

	@Id
	private String id;

	private String tillrinningsarea;

	private String våtmarksyta_S;

	private String vattenyta;
	private String konstruktion;
	private String kommun;
	private String latitud;
	private String longitud;
	private String anläggningsår;
	private String vattendistrikt;
	private String stödform;
	private String område;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTillrinningsarea() {
		return tillrinningsarea;
	}

	public void setTillrinningsarea(String tillrinningsarea) {
		this.tillrinningsarea = tillrinningsarea;
	}

	public String getVåtmarksyta_S() {
		return våtmarksyta_S;
	}

	public void setVåtmarksyta_S(String våtmarksyta_S) {
		this.våtmarksyta_S = våtmarksyta_S;
	}

	public String getVattenyta() {
		return vattenyta;
	}

	public void setVattenyta(String vattenyta) {
		this.vattenyta = vattenyta;
	}

	public String getKonstruktion() {
		return konstruktion;
	}

	public void setKonstruktion(String konstruktion) {
		this.konstruktion = konstruktion;
	}

	public String getKommun() {
		return kommun;
	}

	public void setKommun(String kommun) {
		this.kommun = kommun;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getAnläggningsår() {
		return anläggningsår;
	}

	public void setAnläggningsår(String anläggningsår) {
		this.anläggningsår = anläggningsår;
	}

	public String getVattendistrikt() {
		return vattendistrikt;
	}

	public void setVattendistrikt(String vattendistrikt) {
		this.vattendistrikt = vattendistrikt;
	}

	public String getStödform() {
		return stödform;
	}

	public void setStödform(String stödform) {
		this.stödform = stödform;
	}

	public String getOmråde() {
		return område;
	}

	public void setOmråde(String område) {
		this.område = område;
	}

	@Override
	public String toString() {
		return "Vatmark {" +
				"id='" + id + '\'' +
				", kommun='" + kommun + '\'' +
				", anläggningsår='" + anläggningsår + '\'' +
				", område='" + område + '\'' +
				'}';
	}
}