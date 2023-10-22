package edu.vgtu.project.dto.mail;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailRequestDto {
    String email;
    List<Long> identifiers;
}
