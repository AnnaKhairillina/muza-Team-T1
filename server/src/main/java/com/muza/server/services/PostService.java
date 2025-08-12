package com.muza.server.services;

import com.muza.server.dto.PostDTO;
import com.muza.server.dto.PostResponse;
import com.muza.server.entities.Post;
import com.muza.server.entities.Tag;
import com.muza.server.entities.User;
import com.muza.server.repositories.PostRepository;
import com.muza.server.repositories.TagRepository;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public PostResponse createPost(PostDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setPostTypeId(dto.getPostTypeId());
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setOwnerUserId(user.getId());
        post.setCreationDate(LocalDateTime.now());
        post.setScore(0);

        if (dto.getParentId() != null) {
            post.setParentId(dto.getParentId());
        }

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            String tagsString = dto.getTags().stream()
                    .map(tag -> "<" + tag + ">")
                    .collect(Collectors.joining());
            post.setTags(tagsString);
        }

        Post savedPost = postRepository.save(post);
        return convertToResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponse> getPostById(Integer id) {
        return postRepository.findById(id)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getQuestions(Pageable pageable, String tag) {
        if (tag != null && !tag.isEmpty()) {
            return postRepository.findQuestionsByTag(tag, pageable)
                    .map(this::convertToResponse);
        }
        return postRepository.findByPostTypeIdOrderByCreationDateDesc((short) 1, pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAnswersForQuestion(Integer questionId) {
        return postRepository.findByParentIdAndPostTypeId(questionId, (short) 2)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse updatePost(Integer id, PostDTO dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());

        if (dto.getTags() != null) {
            String tagsString = dto.getTags().stream()
                    .map(tag -> "<" + tag + ">")
                    .collect(Collectors.joining());
            post.setTags(tagsString);
        }

        Post updated = postRepository.save(post);
        return convertToResponse(updated);
    }

    @Transactional
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> searchPosts(String query, Pageable pageable) {
        return postRepository.fullTextSearch(query, pageable)
                .map(this::convertToResponse);
    }

    private PostResponse convertToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setBody(post.getBody());
        response.setScore(post.getScore());
        response.setCreationDate(post.getCreationDate());
        response.setPostTypeId(post.getPostTypeId());

        if (post.getTags() != null) {
            response.setTags(parseTags(post.getTags()));
        }

        if (post.getOwnerUserId() != null) {
            userRepository.findById(post.getOwnerUserId()).ifPresent(user -> {
                response.setUserId(user.getId());
                response.setUsername(user.getDisplayName());
            });
        }

        if (post.getPostTypeId() == 2 && post.getParentId() != null) {
            response.setParentId(post.getParentId());
        }

        return response;
    }

    private List<String> parseTags(String tagsString) {
        if (tagsString == null || tagsString.isEmpty())
            return Collections.emptyList();

        String clean = tagsString.substring(1, tagsString.length() - 1);
        return Arrays.asList(clean.split("><"));
    }
}