package com.example.todoapp.dtos.comment;

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
public class GetCommentDTO implements GetDTO {
        private int page=0;
        private int size=10;
        private String comment;
        private Long todoID;
        private Long commentatorID;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
}
