package io.sunflower.common.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortByLikeCount implements SortStrategy {

    @Override
    public Sort sort(String sortValue) {
        return Sort.by(Sort.Direction.DESC, sortValue);
    }

//    Sort sort = Sort.by(Sort.Order.desc("propertyName1"), Sort.Order.asc("propertyName2"));
//    Slice<Entity> result = entityRepository.findByName("name", PageRequest.of(0, 10, sort));

}
