package test.example.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.example.test.entity.Todo;
import test.example.test.model.TodoResponse;
import test.example.test.model.WebResponse;
import test.example.test.repository.TodoRepository;
import test.example.test.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;


    @PostMapping("/insert")
    public ResponseEntity<String> insertFromApi() {
        todoService.insertFromApi();
        return ResponseEntity.ok("Data inserted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/complete")
    public ResponseEntity<List<Todo>> getCompleteTodos() {
        List<Todo> completeTodos = todoRepository.findByCompleted("true");
        return ResponseEntity.ok(completeTodos);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        // find id if exist
        Optional<Todo> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            Todo todo = existingTodo.get();
            todo.setUserId(updatedTodo.getUserId());
            todo.setTitle(updatedTodo.getTitle());
            todo.setCompleted(updatedTodo.getCompleted());
            todoRepository.save(todo);

            return ResponseEntity.ok("data updated successfully.");
        } else {    
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        // find id if exists
        if (todoRepository.existsById(id)) {
            // delete data by ID
            todoRepository.deleteById(id);
            return ResponseEntity.ok("data deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping(
//            path = "/todo",
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public WebResponse<List<TodoResponse>> read(Todo todo) {
//        List<TodoResponse> todoResponses = todoService.read(todo);
//        return WebResponse.<List<TodoResponse>>builder().data(todoResponses).build();
//    }
//
//    @PostMapping(
//            path = "/todo",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public WebResponse<TodoResponse> create(@RequestBody Todo request) {
//        TodoResponse todoResponse = todoService.create(request);
//        return WebResponse.<TodoResponse>builder().data(todoResponse).build();
//    }

}
