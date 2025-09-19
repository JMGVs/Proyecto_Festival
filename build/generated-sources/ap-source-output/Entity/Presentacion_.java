package Entity;

import Entity.Concierto;
import Entity.Entrada;
import Entity.Escenario;
import Entity.Exposicionarte;
import Entity.Festival;
import Entity.Obrateatro;
import Entity.Persona;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Presentacion.class)
public class Presentacion_ { 

    public static volatile SingularAttribute<Presentacion, Double> costoBase;
    public static volatile SingularAttribute<Presentacion, Festival> idFestival;
    public static volatile CollectionAttribute<Presentacion, Persona> personaCollection;
    public static volatile SingularAttribute<Presentacion, Integer> idPresentacion;
    public static volatile SingularAttribute<Presentacion, String> titulo;
    public static volatile SingularAttribute<Presentacion, Integer> duracion;
    public static volatile SingularAttribute<Presentacion, Exposicionarte> exposicionarte;
    public static volatile SingularAttribute<Presentacion, Concierto> concierto;
    public static volatile CollectionAttribute<Presentacion, Entrada> entradaCollection;
    public static volatile SingularAttribute<Presentacion, Escenario> idEscenario;
    public static volatile SingularAttribute<Presentacion, Obrateatro> obrateatro;

}