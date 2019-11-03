import java.util.*;
import datos.PersonaJDBC;
import domain.Persona;
public class MiEvaluacion {
    public static void main(String[] args) {
        PersonaJDBC personaJDBC = new PersonaJDBC();
        
        List<Persona> personas =  personaJDBC.select();
        for(Persona persona:personas){
            System.out.println("persona: "+persona);
        }
//        Persona persona = new Persona();
//        persona.setNombre("Brenda");
//        persona.setApellido("Infante");
//        persona.setEmail("brenda@gmail-");
//        persona.setTelefono("98252");
//        personaJDBC.insert(persona);
//        

        

        Persona persona = new Persona();
        persona.setIdPersona(2);
        personaJDBC.delete(persona);
        
        
    }
}
