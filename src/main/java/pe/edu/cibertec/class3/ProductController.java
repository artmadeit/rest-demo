package pe.edu.cibertec.class3;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> list() {
        var unaLista = List.of(new Product(null, "Zapatilla", "Nike", 20));
        return unaLista;
    }
}
