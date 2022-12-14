package spring.training.easycook.api;

import org.springframework.web.bind.annotation.*;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.service.TagService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    TagService tagService;

    @PostMapping
    public String create(@Valid  @RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @PutMapping("/{id}")
    public String changeNameTagsById(@PathVariable Long id, @RequestBody String newName) {
        return tagService.changeNameTagsById(id, newName);
    }

    @PutMapping("/{oldName}")
    public String changeNameTagsByName(@PathVariable String oldName, @RequestBody String newName) {
        return tagService.changeNameTagsByName(oldName, newName);
    }

    @GetMapping("/all")
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    @DeleteMapping("/{id}")
    public String deleteTagById(@PathVariable Long id) {
       return tagService.deleteTagById(id);
    }

    @DeleteMapping("/{name}")
    public String deleteTagByName(@PathVariable String name) {
        return tagService.deleteTagByName(name);
    }
    /*
    создать тег null, пустой, слишком длинный,
    получить теги,
    удалить тег (по имени) (а также из рецептов),
    изменить тег (старое и новое имя) (а также для рецептов изменить),
     */
}
