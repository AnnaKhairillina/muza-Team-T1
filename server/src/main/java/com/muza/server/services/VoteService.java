package com.muza.server.services;

import com.muza.server.dto.VoteDTO;
import com.muza.server.entities.Post;
import com.muza.server.entities.User;
import com.muza.server.entities.Vote;
import com.muza.server.repositories.PostRepository;
import com.muza.server.repositories.UserRepository;
import com.muza.server.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void voteForPost(Integer postId, VoteDTO voteDTO) {
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Vote> existingVoteOpt = voteRepository.findByUserIdAndPostId(user.getId(), postId);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();
            int oldValue = existingVote.getVoteTypeId() == 2 ? 1 : -1; // 2=Up, 3=Down
            int newValue = voteDTO.getValue() > 0 ? 2 : 3;

            if (existingVote.getVoteTypeId() == newValue) {
                voteRepository.delete(existingVote);
                updatePostScore(post, -voteDTO.getValue());
            } else {
                existingVote.setVoteTypeId((short) newValue);
                voteRepository.save(existingVote);
                updatePostScore(post, 2 * voteDTO.getValue());
            }
        } else {
            Vote newVote = new Vote();
            newVote.setUserId(user.getId());
            newVote.setPostId(postId);
            newVote.setVoteTypeId(voteDTO.getValue() > 0 ? (short) 2 : (short) 3); // 2=Up, 3=Down
            newVote.setCreationDate(LocalDateTime.now());
            voteRepository.save(newVote);
            updatePostScore(post, voteDTO.getValue());
        }
    }

    private void updatePostScore(Post post, int delta) {
        post.setScore((post.getScore() != null ? post.getScore() : 0) + delta);
        postRepository.save(post);
    }
}