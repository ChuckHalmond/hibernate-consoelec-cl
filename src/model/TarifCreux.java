package model;


import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TarifCreux")
@DiscriminatorValue("1")
public class TarifCreux extends Tarif {

	public TarifCreux(String code, Date heureDebut, Date heureFin,
			float prixKWH, float reduction) {
		super(code, heureDebut, heureFin, prixKWH, reduction);
	}
}
