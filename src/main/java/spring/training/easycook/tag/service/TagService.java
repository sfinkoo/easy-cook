package spring.training.easycook.tag.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.training.easycook.exception.ResourceException;
import spring.training.easycook.exception.ValidationException;
import spring.training.easycook.tag.ValueTypeForTagSearch;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.repository.TagRepository;

import java.util.List;
import java.util.Optional;

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
        tag.setName(newName);
        tagRepository.save(tag);
        log.info("Тэг успешно обновлен.");
        return "Тэг успешно обновлен.";
    }

    public String changeNameTagsByName(String oldName, String newName) {
        Tag tag = findTagByName(oldName);
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

    public String delete(ValueTypeForTagSearch type, String value) {
        Tag tag = findTagByType(type, value);
        tagRepository.deleteById(tag.getId());
        log.info("Тэг успешно удален.");
        return "Тэг успешно удален.";
    }

    public Tag findTagByName(String name) {
        Optional<Tag> foundTag = tagRepository.findAll()
                .stream()
                .filter(tag -> tag.getName().equals(name))
                .findFirst();
        if (foundTag.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким name не найден.");
        }
        return foundTag.get();
    }

    public Tag findTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Тэг с таким id не найден.");
        }
        return tag.get();
    }

    private Tag findTagByType(ValueTypeForTagSearch type, String value) {
        Tag tag;
        if (type.equals(ValueTypeForTagSearch.NAME)) {
            tag = findTagByName(value);
        } else if (type.equals(ValueTypeForTagSearch.ID)) {
            tag = findTagById(Long.parseLong(value));
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Некорректный тип значения.");
        }

        if (tag != null) {
            return tag;
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, String.format("Тэг с таким %s не найден.", type));
        }
    }

    public Tag findTag(ValueTypeForTagSearch type, String value) {
        return findTagByType(type, value);
    }

    public String changeName(ValueTypeForTagSearch type, String value, String newName) throws ValidationException {
        Tag tag;
        if (newName != null && newName.length() >=1 && newName.length() < 100) {
            tag = findTag(type, value);
            tag.setName(newName);
        } else {
            throw new ValidationException("Проверьте данные и повторите запрос.");
        }
        return "Имя тега успешно обновлено.";
    }
}
