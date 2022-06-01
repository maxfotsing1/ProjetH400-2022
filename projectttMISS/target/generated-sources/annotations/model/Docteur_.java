package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Personnes;
import model.Rdv;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-01T20:55:51", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Docteur.class)
public class Docteur_ { 

    public static volatile SingularAttribute<Docteur, Integer> iddocteur;
    public static volatile SingularAttribute<Docteur, String> pseudodocteur;
    public static volatile SingularAttribute<Docteur, Personnes> idpersonnes;
    public static volatile SingularAttribute<Docteur, String> mdpdocteur;
    public static volatile CollectionAttribute<Docteur, Rdv> rdvCollection;

}