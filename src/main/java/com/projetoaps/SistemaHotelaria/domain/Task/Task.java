package com.projetoaps.SistemaHotelaria.domain.Task;

import com.projetoaps.SistemaHotelaria.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "task")
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "taskType", nullable = false)
    private TaskType type;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
