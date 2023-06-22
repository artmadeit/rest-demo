package pe.edu.cibertec.class3.servicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("servicios")
@AllArgsConstructor
public class ServicioController {
    ServicioRepository servicioRepository;

    @GetMapping("{id}")
    public ResponseEntity<Servicio> findById(@PathVariable Long id) {
        return ResponseEntity.of(this.servicioRepository.findById(id));
    }
}
