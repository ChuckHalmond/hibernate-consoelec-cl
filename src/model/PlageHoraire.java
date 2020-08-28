package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.DateFormatters;

@Entity
@Table(name = "PlageHoraire")
public class PlageHoraire {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PlageHoraireID")
	private int plageHoraireID;
	
	@ManyToOne
	@JoinColumn(name = "PlageConsommation")
	private PlageConsommation plageConsommation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateDebut")
	private Date dateDebut;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateFin")
	private Date dateFin;
	
	@ManyToOne
	@JoinColumn(name = "Tarif")
	private Tarif tarif;

	@Column(name = "KWh")
	private Float KWh;

	public PlageHoraire(PlageConsommation plageConsommation, Date dateDebut, Date dateFin, Tarif tarif, float KWh) {
		this.plageConsommation = plageConsommation;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.tarif = tarif;
		this.KWh = KWh;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateTimeFormatter = DateFormatters.getDateTimeFormatter();
		
		return "\nPlageHoraire :" +
			"\n\t- ID : " + getPlageHoraireID() +
			"\n\t- ID plage consommation : " + getPlageConsommation().getPlageConsommationID() +
			"\n\t- Date de début : " + dateTimeFormatter.format(getDateDebut()) +
			"\n\t- Date de fin : " + dateTimeFormatter.format(getDateFin()) +
			"\n\t- Code tarif : " + getTarif().getCode() +
			"\n\t- Consommation (KWh) : " + getKWh();
	}

	public int getPlageHoraireID() {
		return plageHoraireID;
	}

	public void setPlageHoraireID(int plageHoraireID) {
		this.plageHoraireID = plageHoraireID;
	}
	
	public PlageConsommation getPlageConsommation() {
		return plageConsommation;
	}

	public void setPlageConsommation(PlageConsommation plageConsommation) {
		this.plageConsommation = plageConsommation;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	public Float getKWh() {
		return KWh;
	}

	public void setKWh(Float kWh) {
		KWh = kWh;
	}
}
