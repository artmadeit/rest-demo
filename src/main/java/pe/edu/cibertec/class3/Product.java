package pe.edu.cibertec.class3;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Product {
    @Id
    Long id;
    String name;
    String brand;
    Integer stock;
}
