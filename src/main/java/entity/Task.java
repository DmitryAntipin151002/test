package entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
