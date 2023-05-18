package pe.edu.cibertec.class3.veterinario;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("veterinarios")
@AllArgsConstructor
public class VeterinarioController {
    VeterinarioRepository veterinarioRepository;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Veterinario register(@RequestBody Veterinario veterinario) {
        return this.veterinarioRepository.save(veterinario);
    }

    @GetMapping
    public Iterable<Veterinario> list() {
        return this.veterinarioRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Veterinario> findById(@PathVariable Long id) {
        return ResponseEntity.of(this.veterinarioRepository.findById(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        this.veterinarioRepository.deleteById(id);
    }

    // PUT
}
