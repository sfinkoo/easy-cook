package spring.training.easycook.user.entity;

import lombok.*;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.tag.entity.Tag;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "user")
    private Set<Tag> tags;
}
