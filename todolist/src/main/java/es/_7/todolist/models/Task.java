package es._7.todolist.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser user;

    private String title;

    private String description;

    private Time deadline;

    //@Enumerated(EnumType.STRING)
    //private TaskStatus priority;
    private String priority;

    private String completion_status;
}
