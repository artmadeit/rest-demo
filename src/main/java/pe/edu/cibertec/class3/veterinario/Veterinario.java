package pe.edu.cibertec.class3.veterinario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Entity
@Data
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String nombre;
    @NotBlank
    String apellido;
    @Pattern(regexp = "\\d{8}", message = "Formato de dni invalido, debe ser 8 digitos")
    String dni;
    String numeroCertificado;
    String especialidad;

    @PositiveOrZero
    @Max(50_000)
    Integer salario; // Decimal o MonetaryAmount
}
