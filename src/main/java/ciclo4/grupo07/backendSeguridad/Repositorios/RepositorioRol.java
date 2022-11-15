package ciclo4.grupo07.backendSeguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import ciclo4.grupo07.backendSeguridad.Modelos.Rol;

public interface RepositorioRol extends MongoRepository<Rol, String> {


}
