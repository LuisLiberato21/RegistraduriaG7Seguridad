package ciclo4.grupo07.backendSeguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Rol {
    @Id
    private String _id;

    //Variables globales
    private String nombre;
    private String descripcion;

    //Constructor
    public Rol(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Funcion para obtener el _id de mongo
    public String get_id(){
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
