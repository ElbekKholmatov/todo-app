package com.example.todoapp.dtos.todo;

import com.example.todoapp.dtos.GetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTodoDTO implements GetDTO {
        private int page=0;
        private int size=10;
        private String title;
        private String status;
        private String priority;
        private Long creatorID;
        private Long executorID;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
}
