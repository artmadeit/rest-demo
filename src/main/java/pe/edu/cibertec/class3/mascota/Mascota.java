package pe.edu.cibertec.class3.mascota;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Mascota {

    @Id
    private Long id;
    private String nombre;
    private String dueño;
}
