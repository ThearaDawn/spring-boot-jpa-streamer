package com.rean.config;

import com.rean.model.Post;
import com.rean.model.User;
import com.rean.repository.PostRepository;
import com.rean.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FakeDataLoader {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @PostConstruct
    public void loadingData() {

        User user = User.builder()
                .username("demo")
                .password("demo")
                .profileImg("http://localhost:8081/img/profile.png")
                .location("Cambodia")
                .createAt(new Date())
                .status("ACT")
                .build();

        userRepository.save(user);

        List<Post> posts = Arrays.asList(
                new Post(1L, "Demo 1", new Date(), user),
                new Post(2L, "Demo 2", new Date(), user),
                new Post(3L, "Demo 3", new Date(), user),
                new Post(4L, "Demo 4", new Date(), user),
                new Post(5L, "Demo 5", new Date(), user),
                new Post(6L, "Demo 6", new Date(), user),
                new Post(7L, "Demo 7", new Date(), user),
                new Post(8L, "Demo 8", new Date(), user),
                new Post(9L, "Demo 9", new Date(), user),
                new Post(10L, "Demo 10", new Date(), user),
                new Post(11L, "Demo 11", new Date(), user),
                new Post(12L, "Demo 12", new Date(), user),
                new Post(13L, "Demo 13", new Date(), user),
                new Post(14L, "Demo 14", new Date(), user),
                new Post(15L, "Demo 15", new Date(), user)
        );

        postRepository.saveAllAndFlush(posts);
    }
}
