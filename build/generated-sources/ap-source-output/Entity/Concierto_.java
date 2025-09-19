package Entity;

import Entity.Presentacion;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Concierto.class)
public class Concierto_ { 

    public static volatile SingularAttribute<Concierto, Integer> numArtistas;
    public static volatile SingularAttribute<Concierto, Integer> idConcierto;
    public static volatile SingularAttribute<Concierto, Presentacion> presentacion;
    public static volatile SingularAttribute<Concierto, String> generoMusical;

}