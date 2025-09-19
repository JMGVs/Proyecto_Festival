package Entity;

import Entity.Entrada;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(EnumeracionStatus.class)
public class EnumeracionStatus_ { 

    public static volatile SingularAttribute<EnumeracionStatus, Integer> idStatus;
    public static volatile SingularAttribute<EnumeracionStatus, String> descripcion;
    public static volatile CollectionAttribute<EnumeracionStatus, Entrada> entradaCollection;

}