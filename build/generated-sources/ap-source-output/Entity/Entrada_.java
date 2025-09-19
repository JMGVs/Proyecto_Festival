package Entity;

import Entity.EnumeracionStatus;
import Entity.Persona;
import Entity.Presentacion;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Entrada.class)
public class Entrada_ { 

    public static volatile SingularAttribute<Entrada, EnumeracionStatus> idStatus;
    public static volatile SingularAttribute<Entrada, Double> precio;
    public static volatile SingularAttribute<Entrada, Presentacion> idPresentacion;
    public static volatile SingularAttribute<Entrada, String> asiento;
    public static volatile SingularAttribute<Entrada, Integer> idEntrada;
    public static volatile SingularAttribute<Entrada, Persona> idPersona;

}