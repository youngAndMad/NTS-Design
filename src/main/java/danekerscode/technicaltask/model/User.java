package danekerscode.technicaltask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<AmazonFile> files;


    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Log> logs;

}
