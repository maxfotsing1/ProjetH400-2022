package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Personnes;
import model.Rdv;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-01T20:55:52", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Patients.class)
public class Patients_ { 

    public static volatile SingularAttribute<Patients, Personnes> idpersonnes;
    public static volatile SingularAttribute<Patients, String> mdp;
    public static volatile SingularAttribute<Patients, Integer> idpatients;
    public static volatile SingularAttribute<Patients, String> pseudo;
    public static volatile CollectionAttribute<Patients, Rdv> rdvCollection;

}