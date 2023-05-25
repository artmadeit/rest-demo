package pe.edu.cibertec.class3.veterinario;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VeterinarioRepository extends PagingAndSortingRepository<Veterinario, Long> {

    List<Veterinario> findBySalario(Integer salario);

    List<Veterinario> findBySalarioBetween(Integer salarioMin, Integer salarioMax);

    Page<VeterinarioResumen> findResumenBy(Pageable pageable);

    // spring data es un conjunto de librerias que permiten acceder a bases de datos
    // de manera simple y uniforme

    // funciona tanto para bases datos relacionales (oracle, mysql, postgresql, sql
    // server)
    // funciona para bases de datos NoSQL (mongodb, neo4j, cassandra...)

    // List<Veterinario> find__1__By__2__(String dni);
    // 1: lo que tu quieras
    // 2: NombreEquals, Nombre, DniIn

    // findHolaMundoByNombre
    // findHolaMundoByDniIn()

    // Page<VeterinarioResumenDto> findSummaryAll(Pageable pageable);

    // select v from Veterinario where v.nombre = ?
    // List<Veterinario> findByNombre(String nombre);

    // Page<Veterinario> findByNombre(String nombre, Pageable pageable);

    // // select v from Veterinario where v.nombre like %?%
    // List<Veterinario> findByNombreContaining(String nombre);

    // // select v from Veterinario where v.dni = ?
    // List<Veterinario> findByDni(String dni);

    // List<Veterinario> findByDniEquals(String dni);

    // List<Veterinario> readByDni(String dni);

    // List<Veterinario> queryByDni(String dni);

    // List<Veterinario> searchByDni(String dni);

    // select v from Veterinario where v.dni in (?, ?, ?)
    // List<Veterinario> findByDniIn(List<String> dni);

    // select v from Veterinario where v.nombre = ? and v.apellido = ?
    // List<Veterinario> findByNombreAndApellido(String nombre, String apellido);

}
