package com.muza.server.controllers;

import com.muza.server.dto.AnswerResponse;
import com.muza.server.entities.Answer;
import com.muza.server.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponse> createAnswer(
            @RequestBody Answer answer,
            @RequestParam Long questionId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(answerService.createAnswer(answer, questionId, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponse> getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AnswerResponse>> getAllAnswers() {
        return ResponseEntity.ok(answerService.getAllAnswers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Long id,
            @RequestBody Answer updatedAnswer) {
        return ResponseEntity.ok(answerService.updateAnswer(id, updatedAnswer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
