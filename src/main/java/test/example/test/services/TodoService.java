package test.example.test.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import test.example.test.entity.Todo;
import test.example.test.model.TodoResponse;
import org.springframework.stereotype.Service;
import test.example.test.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private RestTemplate restTemplate;

    public void insertFromApi() {
        // clean table before insert
        todoRepository.deleteAll();

        ResponseEntity<Todo[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos/", Todo[].class);
        Todo[] todos = response.getBody();
        for (Todo todo : todos) {
            todoRepository.save(todo);
        }
    }

    //    private TodoResponse toTodoResponse(Todo todo) {
//        return TodoResponse.builder()
//                .id(todo.getId())
//                .user_id(todo.getUser_id())
//                .title(todo.getTitle())
//                .status(todo.getStatus())
//                .build();
//    }
//    @Transactional(readOnly = true)
//    public List<TodoResponse> read(Todo todo) {
//        List<Todo> todos = todoRepository.findAll();
//        return todos.stream().map(this::toTodoResponse).toList();
//    }

//    @Transactional
//    public TodoResponse create(Todo request) {
//
//        if (todoRepository.existsById(request.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is exists");
//        }
//
//        Todo todo = new Todo();
//        todo.setUser_id(request.getUser_id());
//        todo.setTitle(request.getTitle());
//        todo.setStatus(request.getStatus());
//        todoRepository.save(todo);
//
//        return toTodoResponse(todo);
//    }
}
