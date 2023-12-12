package com.example.todoapp.controller.todo;

import com.example.todoapp.dtos.todo.*;
import com.example.todoapp.services.todo.TodoService;
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
@RequestMapping({"/api/v1/todo"})
@RequiredArgsConstructor
@Tag(name = "TodoController", description = "TODO CREATE, UPDATE, SET EXECUTOR, VIEW TODOS AND ETC.")
public class TodoController {

	private final TodoService todoService;

	@Operation(summary = "Create a Todo")
	@ApiResponse(responseCode = "200", description = "Todo created", content = @Content(schema = @Schema(implementation = TodoDTO.class)))
	@PostMapping("/create")
	public ResponseEntity<TodoDTO> create(@RequestBody CreateTodoDTO dto){
		return ResponseEntity.ok(todoService.create(dto));
	}

	@Operation(summary = "Get all Todos")
	@ApiResponse(responseCode = "200", description = "List of Todos retrieved", content = @Content(schema = @Schema(implementation = Page.class)))
	@GetMapping("/get-all-todos")
	public ResponseEntity<Page<TodoDTO>> getAllTodos(
			@Schema(description = "page number",example = "1")
			@RequestParam int page,

			@Schema(description = "count of objects in one page",example = "10")
			@RequestParam int size,

			@RequestParam(required = false) String title,

			@Schema(description = "send one of this statuses TODO|WAITING|IN_PROCESS|DONE",example = "TODO")
			@RequestParam(required = false) String status,

			@Schema(description = "send one of this priorities HIGH|MEDIUM|LOW", example = "MEDIUM")
			@RequestParam(required = false) String priority,

			@Schema(description = "search by creator id")
			@RequestParam(required = false) Long creatorID,

			@Schema(description = "search by executor id")
			@RequestParam(required = false) Long executorID,

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
		GetTodoDTO dto = new GetTodoDTO(page,size,title,status,priority,creatorID,executorID,fDate,tDate);
		return ResponseEntity.ok(todoService.findAll(dto));
	}

	@Operation(summary = "Update a Todo")
	@ApiResponse(responseCode = "200", description = "Todo updated", content = @Content(schema = @Schema(implementation = TodoDTO.class)))
	@PutMapping("/update")
	public ResponseEntity<TodoDTO> update(@RequestBody UpdateTodoDTO dto){
		return ResponseEntity.ok(todoService.update(dto));
	}

	@Operation(summary = "Delete a Todo")
	@ApiResponse(responseCode = "200", description = "Todo deleted", content = @Content(schema = @Schema(implementation = String.class)))
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody DeleteTodoDTO dto){
		todoService.delete(dto);
		return ResponseEntity.ok("todo with this id was deleted");
	}
}