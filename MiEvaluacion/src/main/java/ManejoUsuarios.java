package test;

import java.util.*;
import domain.Usuario;
import datos.*;

public class ManejoUsuarios {
    public static void main(String[] args) {
        
        UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
        
        //INSERTANDO UN NUEVO USUARIO
//        Usuario usuario = new Usuario();
//        usuario.setUsuario("2019DDD");
//        usuario.setPassword("123");
//        usuarioJDBC.insert(usuario);
//        
        listarUsuarios();
        
        //ACTUALIZANDO UN USUARIO
        
        
        
    }
    
    public static void listarUsuarios(){
        UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
        List<Usuario> usuarios =  usuarioJDBC.select();
        for(Usuario usuario:usuarios){
            System.out.println(usuario);
        }
    }
    
    
   
}
