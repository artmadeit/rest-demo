package pe.edu.cibertec.class3.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <= autoincremental
    Long id;
    String firstName;
    String email;

    // @JsonIgnore
    String password;
}
