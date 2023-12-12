package com.example.todoapp.controller.comment;

import com.example.todoapp.dtos.comment.CommentDTO;
import com.example.todoapp.dtos.comment.CreateCommentDTO;
import com.example.todoapp.dtos.comment.GetCommentDTO;
import com.example.todoapp.dtos.todo.CreateTodoDTO;
import com.example.todoapp.dtos.todo.GetTodoDTO;
import com.example.todoapp.dtos.todo.TodoDTO;
import com.example.todoapp.services.comment.CommmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping({"/api/v1/comments"})
@RequiredArgsConstructor
@Tag(name = "CommentController", description = "ADD comment to todo, GET all comments to todo")
public class CommentController {
    private final CommmentService commmentService;

    @Operation(summary = "Add a Comment to Todo")
    @ApiResponse(responseCode = "200", description = "Comment added", content = @Content(schema = @Schema(implementation = CommentDTO.class)))
    @PostMapping("/addComment")
    public ResponseEntity<CommentDTO> create(@RequestBody CreateCommentDTO dto){
        return ResponseEntity.ok(commmentService.create(dto));
    }

    @Operation(summary = "Get all Comments")
    @ApiResponse(responseCode = "200", description = "List of Comments retrieved", content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping("/get-all-comments")
    public ResponseEntity<Page<CommentDTO>> getAllComments(
            @Schema(description = "page number",example = "1")
            @RequestParam int page,

            @Schema(description = "count of objects in one page",example = "10")
            @RequestParam int size,

            @RequestParam(required = false) String comment,

            @Schema(description = "search by todo id")
            @RequestParam(required = false) Long todoID,

            @Schema(description = "search by commentator id")
            @RequestParam(required = false) Long commentatorID,

            @Schema(description = "year-month-day hour:minute:second AM/PM",example = "2023-01-01 00:00:00 AM")
            @RequestParam(required = false) String fromDate,

            @Schema(description = "year-month-day hour:minute:second AM/PM",example = "2023-01-01 00:00:00 AM")
            @RequestParam(required = false) String toDate
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        LocalDateTime fDate = null;
        LocalDateTime tDate = null;
        if (fromDate!=null)
            fDate = LocalDateTime.parse(fromDate, formatter);
        if (toDate!=null)
            tDate = LocalDateTime.parse(toDate, formatter);
        GetCommentDTO dto = new GetCommentDTO(page,size,comment,todoID,commentatorID,fDate,tDate);
        return ResponseEntity.ok(commmentService.findAll(dto));
    }
}
