package datos;

import java.util.List;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class UsuarioJDBC {
    public Connection conexionTransaccional;
    private static final String SQL_SELECT = "SELECT idusuario, usuario, password from usuario;";
    private static final String SQL_UPDATE = "UPDATE usuario set usuario=?, password=? where idusuario=?";
    private static final String SQL_DELETE = "DELETE usuario where idusuario=?";
    private static final String SQL_INSERT = "INSERT usuario (usuario, password ) VALUES (?,?)";
    
    public UsuarioJDBC(){}

    public UsuarioJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }
    
    
    public List<Usuario> select() throws SQLException{
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Connection cn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario=null;
        try {
            cn = this.conexionTransaccional !=null?this.conexionTransaccional:Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            System.out.println("EJECUTANDO QUERY: "+SQL_SELECT);
            while(rs.next()){
                int iduser = rs.getInt("idusuario");
                String user = rs.getString("usuario");
                String pass = rs.getString("password");                
                usuario = new Usuario();
                usuario.setIdUsuario(iduser);
                usuario.setUsuario(user);
                usuario.setPassword(pass);
                usuarios.add(usuario);
               
            }
            System.out.println("TOTAL DE USUARIOS "+usuarios.size());
        } finally{
            Conexion.close(stmt);
            Conexion.close(rs);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
    
    return usuarios;
    }
    
    public int insert(Usuario usuario) {
        int rows = 0;
        Connection cn = null;
        PreparedStatement stmt=null;
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            System.out.println("EJECUTANDO QUERY: "+SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("REGISTROS INSERTADOS: "+rows);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            Conexion.close(stmt);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
        return rows;
    }

    public int update(Usuario usuario) throws SQLException {
        int rows = 0;
        Connection cn = null;
        PreparedStatement stmt = null;
        try {
            cn = this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdUsuario());
        } finally{
            Conexion.close(stmt);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
        return rows;
    }

    public int delete(Usuario usuario) throws SQLException {
        int rows = 0;
        Connection cn = null;
        PreparedStatement stmt = null;
        try {
            cn = this.conexionTransaccional != null? this.conexionTransaccional:Conexion.getConnection();
            stmt = cn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getIdUsuario());
            System.out.println("EJECUTANDO QUERY: "+SQL_DELETE);
            rows=stmt.executeUpdate();
            System.out.println("REGISTROS ELIMINADOS: "+rows);
        }finally{
            Conexion.close(stmt);
            if(this.conexionTransaccional==null){
                Conexion.close(cn);
            }
        }
        return rows;
    }
}
