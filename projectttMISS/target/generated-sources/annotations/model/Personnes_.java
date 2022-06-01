package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Docteur;
import model.Patients;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-01T20:55:51", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Personnes.class)
public class Personnes_ { 

    public static volatile CollectionAttribute<Personnes, Docteur> docteurCollection;
    public static volatile SingularAttribute<Personnes, String> firstName;
    public static volatile SingularAttribute<Personnes, String> lastName;
    public static volatile SingularAttribute<Personnes, Date> dateofbirth;
    public static volatile SingularAttribute<Personnes, Integer> idpersonnes;
    public static volatile SingularAttribute<Personnes, String> numeroRN;
    public static volatile SingularAttribute<Personnes, String> adresse;
    public static volatile SingularAttribute<Personnes, String> tel;
    public static volatile CollectionAttribute<Personnes, Patients> patientsCollection;

}