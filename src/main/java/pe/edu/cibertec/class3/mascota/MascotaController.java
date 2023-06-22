package pe.edu.cibertec.class3.mascota;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("mascotas")
@Tag(name = "mascotas")
public class MascotaController {

    MascotaRepository mascotaRepository;

    // @GetMapping
    // public Iterable<Mascota> listar() {
    // return mascotaRepository.findAll();
    // }

    @GetMapping
    public Page<Mascota> listar(@ParameterObject Pageable pageable) {
        return mascotaRepository.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Mascota> findById(@PathVariable Long id) {
        return ResponseEntity.of(mascotaRepository.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mascota register(@RequestBody Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        mascotaRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Mascota edit(@PathVariable Long id, @RequestBody Mascota mascota) {
        // Mascota newMascota = new Mascota();
        // newMascota.setId(id);
        // newMascota.setNombre(mascota.getNombre());
        // newMascota.setDueño(mascota.getDueño());

        mascota.setId(id);
        return mascotaRepository.save(mascota);
    }

    @PatchMapping("{id}")
    public Mascota partiallyEdit(@PathVariable Long id, @RequestBody Mascota requestMascota) {
        Mascota mascota = mascotaRepository.findById(id).orElseThrow();

        if (requestMascota.getNombre() != null) {
            mascota.setNombre(requestMascota.getNombre());
        }

        if (requestMascota.getDueño() != null) {
            mascota.setDueño(requestMascota.getDueño());
        }

        return mascotaRepository.save(mascota);
    }

}
