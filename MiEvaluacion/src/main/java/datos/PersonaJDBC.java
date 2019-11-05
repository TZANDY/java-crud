package datos;
import domain.Persona;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonaJDBC {
    
    //CREO LA VARIABLE TRANSACCIONAL CON EL OBJETIVO DE MANTENER UNA TRANSACCION QUE PUEDA REALIZARSE ROLLBAK Y COMMIT
    private Connection conexionTransaccional;
    private static final String SQL_SELECT="SELECT idpersona,nombre,apellido,email,telefono from persona;";
    private static final String SQL_UPDATE="UPDATE persona set nombre=?,apellido=?,email=?,telefono=? where idpersona=?";
    private static final String SQL_INSERT="INSERT into persona(nombre,apellido,email,telefono) values (?,?,?,?)";
    private static final String SQL_DELETE="DELETE FROM persona where idpersona=?";

    //AGREGO LOS CONSTRUCTORES PORQUE SON NECESARIOS PARA LA TRANSACCION
    public PersonaJDBC() {
    }
    public PersonaJDBC(Connection conexionTransaccional){
        this.conexionTransaccional=conexionTransaccional;
    }
        
    public List<Persona> select() throws SQLException{
        //inicializo variables de conexion
        Connection cn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Persona persona = null;
        List<Persona> personas = new ArrayList<Persona>();

        //ABRE EL TRY CATCH
        try {
            //INSTANCIO EL METODO CON LA VARIABLE
            //HE CREADO LA TRANSACCION PARA PODER REALIZAR COMMIT Y ROLLBACK
            cn =  this.conexionTransaccional !=null? this.conexionTransaccional: Conexion.getConnection();
            //LEO LA SENTENCIA CON EL PREPARESTATEMENT
            stmt=cn.prepareStatement(SQL_SELECT);
            //el resultset recibe el resultado del preparestatement
            rs=stmt.executeQuery();
            while (rs.next()) {                
                // declaro variables para asignar los resultados
                int id=rs.getInt("idpersona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email= rs.getString("email");
                String telefono=rs.getString("telefono");
                
                //instancio la clase persona y asigno a cada atributo
                persona = new Persona();
                persona.setIdPersona(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setEmail(email);
                persona.setTelefono(telefono);
                personas.add(persona);
            }
            
        } 
        finally{
            //CERRAR NECESARIAMENTE CON LA CLASE CONEXION(CN ,STMT Y RS)
            Conexion.close(stmt);
            Conexion.close(rs);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
        return personas;
    }
    
    public int insert(Persona persona) throws SQLException{
    int rows=0;
    Connection cn = null;
    PreparedStatement stmt=null;
        try {
            cn= this.conexionTransaccional !=null? this.conexionTransaccional: Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            
            System.out.println("EJECUTANDO QUERY :"+SQL_INSERT);
            rows=stmt.executeUpdate();
            System.out.println("REGISTROS INSERTADOS: "+rows);
        } finally{
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
            Conexion.close(stmt);
        }
    return rows;
    }
    
    public int update(Persona persona) throws SQLException{
        int rows=0;
        Connection cn=null;
        PreparedStatement stmt=null;
        try {
            cn = this.conexionTransaccional !=null? this.conexionTransaccional: Conexion.getConnection();
            stmt=cn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getIdPersona());
            System.out.println("EJECUTANDO QUERY: "+SQL_UPDATE);
            rows=stmt.executeUpdate();
            System.out.println("REGISTROS ACTUALIZADOS: "+rows);
            
        } finally{
            Conexion.close(stmt);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
        return rows;
    }
    public int delete(Persona persona) throws SQLException{
    int rows=0;
    Connection cn = null;
    PreparedStatement stmt = null;
        try {
            cn = this.conexionTransaccional !=null? this.conexionTransaccional: Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, persona.getIdPersona());
            System.out.println("EJECUTANDO QUERY: "+SQL_DELETE);
            rows=stmt.executeUpdate();
            System.out.println("REGISTROS ELIMINADOS: "+rows);
        } finally{
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
            Conexion.close(stmt);
        }
    return rows;
    }
    
}
