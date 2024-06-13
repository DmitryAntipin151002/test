package entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProjectUser {
    private Long projectId; // Foreign key to Project
    private Long userId;    // Foreign key to User
}