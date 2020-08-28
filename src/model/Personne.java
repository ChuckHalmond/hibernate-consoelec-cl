package model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Personne")
public class Personne {
	@Id
	@Column(name = "NSS")
	private int NSS;
	
	@OneToOne
	@JoinColumn(name = "Adresse")
	private Adresse adresse;
	
	@Column(name = "Telephone")
	private String telephone;

	public Personne(int NSS, Adresse adresse, String telephone) {
		this.NSS = NSS;
		this.adresse = adresse;
		this.telephone = telephone;
	}
	
	@Override
	public String toString() {
		return "\nPersonne :" +
			"\n\t- NSS : " + getNSS() +
			"\n\t- Adresse : " + getAdresse() +
			"\n\t- Téléphone : " + getTelephone();
	}

	public int getNSS() {
		return NSS;
	}

	public void setNSS(int NSS) {
		this.NSS = NSS;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
