package model;


import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TarifPlein")
@DiscriminatorValue("2")
public class TarifPlein extends Tarif {

	public TarifPlein(String code, Date heureDebut, Date heureFin, float prixKWH) {
		super(code, heureDebut, heureFin, prixKWH, 0);
	}
}
