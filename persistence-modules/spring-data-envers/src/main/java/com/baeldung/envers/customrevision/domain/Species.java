package com.baeldung.envers.customrevision.domain;

<<<<<<< HEAD
=======
import com.baeldung.envers.customrevision.repository.SpeciesRepository;
>>>>>>> 35852659c4 ([BAEL-8592] Article code)
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Data
@NoArgsConstructor
public class Species {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

<<<<<<< HEAD
    public static Species forName(String name) {
=======
    public static Species valueOf(String name) {
>>>>>>> 35852659c4 ([BAEL-8592] Article code)
        var s = new Species();
        s.setName(name);
        return s;
    }
}
