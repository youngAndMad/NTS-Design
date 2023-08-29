package danekerscode.technicaltask.model;

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
    private String name;
    private String email;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private List<AmazonFile> files;


    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private List<Log> logs;

}
