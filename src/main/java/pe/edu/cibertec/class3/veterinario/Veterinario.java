package pe.edu.cibertec.class3.veterinario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;
    String apellido;
    String dni;
    String numeroCertificado;
    String especialidad;
    Integer salario; // Decimal o MonetaryAmount
}
