package spring.training.easycook.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.training.easycook.tag.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
