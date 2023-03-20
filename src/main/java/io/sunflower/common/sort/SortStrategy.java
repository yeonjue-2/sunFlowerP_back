package io.sunflower.common.sort;

import org.springframework.data.domain.Sort;

public interface SortStrategy {
    /*
     *  @return Pageable에 필요한 sort 반환
     * */
    Sort sort(String sortValue);
}
