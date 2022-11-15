package ciclo4.grupo07.backendSeguridad.Controladores;


import ciclo4.grupo07.backendSeguridad.Modelos.Permiso;
import ciclo4.grupo07.backendSeguridad.Modelos.PermisosRoles;
import ciclo4.grupo07.backendSeguridad.Modelos.Rol;
import ciclo4.grupo07.backendSeguridad.Repositorios.RepositorioPermiso;
import ciclo4.grupo07.backendSeguridad.Repositorios.RepositorioPermisosRoles;
import ciclo4.grupo07.backendSeguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {
    @Autowired
    private RepositorioPermisosRoles miRepositorioPermisoRoles;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisosRoles> index(){
        return miRepositorioPermisoRoles.findAll();
    }

    /**
     * Asignacion rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles create(@PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRoles nuevo = new PermisosRoles();
        Rol elRol = miRepositorioRol.findById(id_rol).orElse(null);
        Permiso elPermiso = miRepositorioPermiso.findById(id_permiso).orElse(null);
        if(elRol != null && elPermiso != null){
            nuevo.setRol((elRol));
            nuevo.setPermiso((elPermiso));
            return miRepositorioPermisoRoles.save(nuevo);
        } else {
            return null;
        }
    }

    @GetMapping("{id_PermisosRoles}")
    public PermisosRoles show(@PathVariable String id){
        PermisosRoles permisosRolesActual = miRepositorioPermisoRoles.findById(id).orElse(null);
        return permisosRolesActual;
    }

    /**
     * Modificacion Rol y Permiso
     * @param id_PermisosRoles
     * @param id_rol
     * @param id_permiso
     * @return
     */

    @PutMapping("{id_PermisosRoles}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles update(@PathVariable String id_PermisosRoles, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRoles permisosRolesActual = miRepositorioPermisoRoles.findById(id_PermisosRoles).orElse(null);
        Rol elRol = miRepositorioRol.findById(id_rol).orElse(null);
        Permiso elPermiso = miRepositorioPermiso.findById(id_permiso).orElse(null);
        if(permisosRolesActual != null && elRol != null && elPermiso != null){
            permisosRolesActual.setPermiso(elPermiso);
            permisosRolesActual.setRol(elRol);
            return miRepositorioPermisoRoles.save(permisosRolesActual);
        } else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        PermisosRoles permisosRolesActual = miRepositorioPermisoRoles.findById(id).orElse(null);
        if(permisosRolesActual != null){
            miRepositorioPermisoRoles.delete(permisosRolesActual);
        }
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRoles getPermisos(@PathVariable String id_rol, @RequestBody Permiso infoPermiso){
        Permiso elPermiso = miRepositorioPermiso.getPermiso(infoPermiso.getUrl(), infoPermiso.getMetodo());
        Rol elRol = miRepositorioRol.findById(id_rol).get();
        if(elPermiso!=null && elRol!=null){
            return miRepositorioPermisoRoles.getPermisoRol(elRol.get_id(), elPermiso.get_id());
        }else{
            return null;
        }
    }
}
