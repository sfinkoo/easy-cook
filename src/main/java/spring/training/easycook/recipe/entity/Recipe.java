package spring.training.easycook.recipe.entity;

import lombok.*;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "kcals")
    private Float kcals;

    @Column(name = "protein")
    private Float protein;

    @Column(name = "fat")
    private Float fat;

    @Column(name = "carbon")
    private Float carbon;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tag_recipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
