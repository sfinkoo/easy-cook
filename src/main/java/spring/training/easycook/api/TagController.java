package spring.training.easycook.api;

import org.springframework.web.bind.annotation.*;
import spring.training.easycook.exception.ValidationException;
import spring.training.easycook.tag.ValueTypeForTagSearch;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.service.TagService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public String create(@Valid @RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @PutMapping
    public String changeName(@RequestBody String newName,
                             @RequestParam("type") ValueTypeForTagSearch type,
                             @RequestParam("value") String value) throws ValidationException {
        return tagService.changeName(type, value, newName);
    }

    @GetMapping("/all")
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    @GetMapping()
    public Tag find(@RequestParam("type") ValueTypeForTagSearch type,
                    @RequestParam("value") String value) {
        return tagService.findTag(type, value);
    }

    @DeleteMapping
    public String delete(@RequestParam("type") ValueTypeForTagSearch type,
                         @RequestParam("value") String value) {
        return tagService.delete(type, value);
    }

    /*
    создать тег null, пустой, слишком длинный,
    получить теги,
    удалить тег (по имени) (а также из рецептов),
    изменить тег (старое и новое имя) (а также для рецептов изменить),
     */
}
