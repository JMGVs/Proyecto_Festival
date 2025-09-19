package Entity;

import Entity.Presentacion;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Escenario.class)
public class Escenario_ { 

    public static volatile SingularAttribute<Escenario, String> ubicacion;
    public static volatile CollectionAttribute<Escenario, Presentacion> presentacionCollection;
    public static volatile SingularAttribute<Escenario, String> nombre;
    public static volatile SingularAttribute<Escenario, Integer> idEscenario;
    public static volatile SingularAttribute<Escenario, Integer> capacidad;

}