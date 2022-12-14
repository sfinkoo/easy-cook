package spring.training.easycook.tag.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.training.easycook.exception.ResourceException;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.repository.TagRepository;

import java.util.List;

@Slf4j
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public String create(Tag tag) {
        tagRepository.save(tag);
        log.info("Тэг успешно добавлен");
        return String.format("Тэг успешно добавлен, его id - %s", tag.getId());
    }

    public String changeNameTagsById(Long id, String newName) {
        Tag tag = findTagById(id);
        if (tag == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким id не найден.");
        }
        tag.setName(newName);
        tagRepository.save(tag);
        log.info("Тэг успешно обновлен.");
        return "Тэг успешно обновлен.";
    }

    public String changeNameTagsByName(String oldName, String newName) {
        Tag tag = findTagByName(oldName);
        if (tag == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким id не найден.");
        }
        tag.setName(newName);
        tagRepository.save(tag);
        log.info("Тэг успешно обновлен.");
        return "Тэг успешно обновлен.";
    }

    public List<Tag> getAll() {
        if (tagRepository.findAll().isEmpty()) {
            log.info("Список тэгов пока пуст.");
        }
        return tagRepository.findAll();
    }

    public String deleteTagByName(String name) {
        Tag tag = findTagByName(name);
        if (tag == null) {
            log.info("Тэг с таким name не найден.");
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким name не найден.");
        }
        tagRepository.deleteById(tag.getId());
        log.info("Тэг успешно удален.");
        return "Тэг успешно удален.";
    }

    public String deleteTagById(Long id) {
        Tag tag = findTagById(id);
        if (tag == null) {
            log.info("Тэг с таким id не найден.");
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким name не найден.");
        }
        tagRepository.deleteById(id);
        log.info("Тэг успешно удален.");
        return "Тэг успешно удален.";
    }

    private Tag findTagByName(String name) {
        return tagRepository.findAll()
                .stream()
                .filter(tag -> tag.getName().equals(name))
                .toList().get(0);
    }

    private Tag findTagById(Long id) {
        return tagRepository.findAll()
                .stream()
                .filter(tag -> tag.getId().equals(id))
                .toList().get(0);
    }
}
