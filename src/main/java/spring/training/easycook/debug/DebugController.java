package spring.training.easycook.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.recipe.repository.RecipeRepository;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.repository.TagRepository;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.repository.UserRepository;

import java.time.LocalDateTime;

@RestController
public class DebugController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    RecipeRepository recipeRepository;


    @GetMapping("/debug")
    public void debug(){
        Recipe rec = new Recipe();
        rec.setName("chik");
        rec.setDescription("d;lfk;ld");
        rec.setCreated(LocalDateTime.now());

        Tag tag = new Tag();
        tag.setName("chiken");

        var u = new User();
        u.setPassword("u-pass");
        u.setUsername("u-name");

        System.out.println();
    }
}
