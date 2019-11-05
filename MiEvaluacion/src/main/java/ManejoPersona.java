
import datos.Conexion;
import datos.PersonaJDBC;
import java.sql.*;
import domain.Persona;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Infan
 */
public class ManejoPersona {
    public static void main(String[] args) {
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            //por defecto el autocommit esta activado pero debemos desactivarlo para realizarlo nosotros el autocomit
            if(conexion.getAutoCommit()==true){conexion.setAutoCommit(false);}
            PersonaJDBC personaJDBC = new PersonaJDBC(conexion);
            
            Persona persona = new Persona();
            persona.setIdPersona(1);
            persona.setNombre("Andi Joel");
            persona.setApellido("Infante");
            persona.setTelefono("982522252");
            persona.setEmail("andi@gmail.com");
            personaJDBC.update(persona);
            
            Persona persona2 = new Persona();
            persona2.setNombre("Ricardo");
            persona2.setApellido("Infante");
            persona2.setTelefono("99999");
            persona2.setEmail("ricardo@gmail.com");
            personaJDBC.insert(persona2);
            
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                //rollback todas las transacciones realizadas no se van a a guardar
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
;
        }
    }
    
}
