package spring.training.easycook.tag.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    //todo move to dto layer
    @NotNull
    @Length(min = 1, max = 100, message = "Тэг может содержать от 1 до 100 символов.")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
