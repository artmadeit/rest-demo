package pe.edu.cibertec.class3.veterinario;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("veterinarios")
@AllArgsConstructor
@Validated
public class VeterinarioController {
    VeterinarioRepository veterinarioRepository;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Veterinario register(@RequestBody @Valid Veterinario veterinario) {
        return this.veterinarioRepository.save(veterinario);
    }

    @GetMapping
    public Page<Veterinario> list(Pageable pageable) {
        return this.veterinarioRepository.findAll(pageable);
    }

    @GetMapping("salarios")
    public List<Veterinario> findBySalarioBetween(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer salarioMin,
            @PositiveOrZero @RequestParam(required = true) Integer salarioMax) {

        if (salarioMax < salarioMin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salario max no puede ser menor al salario min");
        }

        return veterinarioRepository.findBySalarioBetween(salarioMin, salarioMax);
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
