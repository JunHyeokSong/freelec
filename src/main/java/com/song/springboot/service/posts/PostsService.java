package com.song.springboot.service.posts;

import com.song.springboot.domain.posts.Posts;
import com.song.springboot.domain.posts.PostsRepository;
import com.song.springboot.web.dto.PostsListResponseDto;
import com.song.springboot.web.dto.PostsResponseDto;
import com.song.springboot.web.dto.PostsSaveRequestDto;
import com.song.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("No such post found. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("No such post found. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("No such post found. id="+id));

        postsRepository.delete(posts);
    }
}
