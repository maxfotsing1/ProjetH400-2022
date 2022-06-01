package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Docteur;
import model.Patients;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-01T20:55:51", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Rdv.class)
public class Rdv_ { 

    public static volatile SingularAttribute<Rdv, Date> daterdv;
    public static volatile SingularAttribute<Rdv, Docteur> docteur;
    public static volatile SingularAttribute<Rdv, Date> heurerdv;
    public static volatile SingularAttribute<Rdv, Patients> patients;
    public static volatile SingularAttribute<Rdv, String> presence;
    public static volatile SingularAttribute<Rdv, Integer> idrdv;
    public static volatile SingularAttribute<Rdv, String> pec;

}