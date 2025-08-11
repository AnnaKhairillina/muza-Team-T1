package com.muza.server.controllers;

import com.muza.server.dto.VoteDTO;
import com.muza.server.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/question/{questionId}")
    public ResponseEntity<Void> voteQuestion(@PathVariable Long questionId, @RequestBody VoteDTO voteDTO) {
        voteService.voteForQuestion(questionId, voteDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/answer/{answerId}")
    public ResponseEntity<Void> voteAnswer(@PathVariable Long answerId, @RequestBody VoteDTO voteDTO) {
        voteService.voteForAnswer(answerId, voteDTO);
        return ResponseEntity.ok().build();
    }
}
