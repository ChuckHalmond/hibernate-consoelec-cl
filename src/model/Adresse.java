package model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Adresse")
public class Adresse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AdresseID")
	private int adresseID;
	
	@Column(name = "Rue")
	private String rue;
	
	@Column(name = "Ville")
	private String ville;
	
	@Column(name = "CodePostal")
	private int codePostal;
	
	@Column(name = "Pays")
	private String pays;

	public Adresse(String rue, String ville, int codePostal, String pays) {
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
		this.pays = pays;
	}
	
	@Override
	public String toString() {
		return "\nAdresse :" +
			"\n\t- Rue : " + getRue() +
			"\n\t- Ville : " + getVille() +
			"\n\t- Code postal : " + getCodePostal() +
			"\n\t- Pays : " + getPays();
	}

	public int getAdresseID() {
		return adresseID;
	}

	public void setAdresseID(int adresseID) {
		this.adresseID = adresseID;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
}
