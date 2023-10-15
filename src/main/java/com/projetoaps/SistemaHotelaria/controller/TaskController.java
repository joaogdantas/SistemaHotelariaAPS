package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.Task.*;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import com.projetoaps.SistemaHotelaria.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private Task task;
    private User user;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity assignTask(@RequestBody CreateTaskDTO data){
        Task task = new Task();

        task.setDescription(data.description());
        task.setType(data.type());
        task.setDone(false);

        Optional<User> optionalUser = userRepository.findById(data.userId());
        if (optionalUser.isPresent()) {
            task.setUser(optionalUser.get());
        } else {
            return ResponseEntity.badRequest().body("Não existe uma conta com esse id registrado, por favor, atribua um válido");
        }

        Task savedTask = taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa atribuída com sucesso");
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<ReturnEmployeesTaskListDTO>> findAllTasksOfEmployee(@PathVariable UUID userId) {

        Optional<User> existentUser = userRepository.findById(userId);

        if(existentUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }

        User findedUser = existentUser.get();
        List<Task> tasks = findedUser.getTasks();

        List<ReturnEmployeesTaskListDTO> result = new ArrayList<>();

        tasks.forEach(t -> result.add(new ReturnEmployeesTaskListDTO(t.getId(), t.getDescription(), t.getType(), t.getDone())));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity updateTaskDescription(@RequestBody @Valid ManagerUpdateTaskDescriptionDTO data, @PathVariable UUID id){
        Optional<Task> existentTask = taskRepository.findById(id);

        if (existentTask.isPresent()) {
            Task findedTask = existentTask.get();
            findedTask.setDescription(data.description());

            taskRepository.save(findedTask);

            return ResponseEntity.status(HttpStatus.OK).body("Descrição da tarefa atualizada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada, insira um id existente, por favor.");
    }

    @PutMapping("/done/{id}")
    @Transactional
    public ResponseEntity doneTask(@PathVariable UUID id){

        Optional<Task> existentTask = taskRepository.findById(id);

        if (existentTask.isPresent()) {
            Task findedTask = existentTask.get();
            findedTask.setDone(true);

            taskRepository.save(findedTask);

            return ResponseEntity.status(HttpStatus.OK).body("Tarefa marcada como finalizada!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada, insira um id existente, por favor.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTask(@PathVariable UUID id){

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task deletedTask = optionalTask.get();

            taskRepository.delete(deletedTask);

            return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada, digite um id existente, por favor");
        }
    }
}
