package Entity;

import Entity.Entrada;
import Entity.Presentacion;
import Entity.Usuario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-09-18T20:50:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile CollectionAttribute<Persona, Presentacion> presentacionCollection;
    public static volatile SingularAttribute<Persona, Usuario> idUsuario;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, Integer> edad;
    public static volatile SingularAttribute<Persona, Integer> idPersona;
    public static volatile CollectionAttribute<Persona, Entrada> entradaCollection;

}