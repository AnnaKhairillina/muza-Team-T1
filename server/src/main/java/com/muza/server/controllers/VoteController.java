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

    @PostMapping("/post/{postId}")
    public ResponseEntity<Void> voteForPost(
            @PathVariable Integer postId,
            @RequestBody VoteDTO voteDTO) {
        voteService.voteForPost(postId, voteDTO);
        return ResponseEntity.ok().build();
    }
}