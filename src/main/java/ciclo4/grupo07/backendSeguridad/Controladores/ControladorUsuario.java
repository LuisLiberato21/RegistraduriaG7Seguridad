package ciclo4.grupo07.backendSeguridad.Controladores;


import ciclo4.grupo07.backendSeguridad.Modelos.Rol;
import ciclo4.grupo07.backendSeguridad.Modelos.Usuario;
import ciclo4.grupo07.backendSeguridad.Repositorios.RepositorioRol;
import ciclo4.grupo07.backendSeguridad.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Usuario> index(){
        return miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        infoUsuario.setContraseña(convertirSHA256(infoUsuario.getContraseña()));
        return miRepositorioUsuario.save(infoUsuario);
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual = miRepositorioUsuario.findById(id).orElse(null);
        return usuarioActual;
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario){
        Usuario usuarioActual = miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual!=null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContraseña(convertirSHA256(infoUsuario.getContraseña()));
            return miRepositorioUsuario.save(usuarioActual);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual = miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual != null){
            miRepositorioUsuario.delete(usuarioActual);
        }
    }


    /**
     *
     * @param id_usuario
     * @param id_rol
     * @return Usuario
     */
    @PutMapping("{id_usuario}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id_usuario, @PathVariable String id_rol){
        Usuario usuarioActual = miRepositorioUsuario.findById(id_usuario).orElse(null);
        Rol rolActual = miRepositorioRol.findById(id_rol).orElse(null);
        if(usuarioActual != null && rolActual != null){
            usuarioActual.setRol(rolActual);
            return miRepositorioUsuario.save(usuarioActual);
        } else {
            return null;
        }
    }

    @PostMapping("/validar")
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException{
        Usuario usuarioActual = miRepositorioUsuario.getUserByMail(infoUsuario.getCorreo());
        if(usuarioActual != null && usuarioActual.getContraseña().equals(convertirSHA256(infoUsuario.getCorreo()))){
            usuarioActual.setContraseña((""));
            return usuarioActual;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    public String convertirSHA256(String password){
        MessageDigest md = null;
        try{
                md = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b: hash){
            sb.append(String.format("%20x", b));
        }
        return sb.toString();
    }

}
