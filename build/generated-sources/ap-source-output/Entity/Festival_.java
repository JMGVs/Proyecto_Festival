package Entity;

import Entity.Presentacion;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Festival.class)
public class Festival_ { 

    public static volatile SingularAttribute<Festival, Integer> idFestival;
    public static volatile SingularAttribute<Festival, Date> fechaInicio;
    public static volatile CollectionAttribute<Festival, Presentacion> presentacionCollection;
    public static volatile SingularAttribute<Festival, Date> fechaFinal;
    public static volatile SingularAttribute<Festival, String> nombre;

}