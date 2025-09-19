/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author juanm
 */
public class Persistencia {
      private  EntityManagerFactory emf;
 
    public  EntityManagerFactory getEmf(){
        if(emf==null){ // verifica si hay una conexcion de no ser asi crea uno
            emf= Persistence.createEntityManagerFactory("No_MamadasPU");
            // toma como referencia siempre la conexion que esta en la persistencia.
        }
        return emf; // me retorna la conexion para ocuparla en otras clases
    }
}
