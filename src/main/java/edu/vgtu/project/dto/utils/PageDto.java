package edu.vgtu.project.dto.utils;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto<T> {
    List<T> content;
    Long currentPage;
    Long totalCount;
}
