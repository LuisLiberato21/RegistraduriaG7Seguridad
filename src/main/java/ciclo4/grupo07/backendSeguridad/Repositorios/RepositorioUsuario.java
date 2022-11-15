package ciclo4.grupo07.backendSeguridad.Repositorios;

import ciclo4.grupo07.backendSeguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioUsuario extends MongoRepository<Usuario, String> {
    @Query("{'correo':?0}")
    public Usuario getUserByMail(String correo);
}
