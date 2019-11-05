import java.util.*;
import domain.Usuario;
import datos.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoUsuarios {
    public static void main(String[] args) {
        Connection conexion=null;
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()==true){conexion.setAutoCommit(false);}
            UsuarioJDBC usuarioJDBC = new UsuarioJDBC(conexion);
           
            Usuario usuario = new Usuario();
            usuario.setUsuario("King");
            usuario.setPassword("1");
            usuarioJDBC.insert(usuario);
            
            Usuario usuario1 = new Usuario();
            usuario1.setIdUsuario(1);
            usuario1.setUsuario("ANDI JOEL");
            usuario1.setPassword("123456");
            usuarioJDBC.update(usuario1);
            
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
             System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }   
        } 
    }
    /*
    public static void listarUsuarios(){
        UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
        List<Usuario> usuarios =  usuarioJDBC.select();
        for(Usuario usuario:usuarios){
            System.out.println(usuario);
        }
    }*/
    
    
   
}
