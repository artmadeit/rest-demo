package pe.edu.cibertec.class3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MascotaController {

    MascotaRepository mascotaRepository;

    @GetMapping("mascotas")
    public Iterable<Mascota> listar() {
        return mascotaRepository.findAll();
    }
}
