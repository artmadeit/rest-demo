package pe.edu.cibertec.class3.veterinario;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface VeterinarioRepository extends PagingAndSortingRepository<Veterinario, Long> {

    List<Veterinario> findBySalario(Integer salario);

    List<Veterinario> findBySalarioBetween(Integer salarioMin, Integer salarioMax);

}
