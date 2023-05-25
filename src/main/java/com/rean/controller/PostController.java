package com.rean.controller;

import com.rean.data.PostResponse;
import com.rean.model.Post;
import com.rean.model.Post$;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PostController {

    @Autowired
    private JPAStreamer jpaStreamer;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostResponse> posts(
            @RequestParam(required = false, defaultValue = "0") long page,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ){

        return jpaStreamer.stream(Post.class)
                .skip(page * pageSize)
                .limit(pageSize)
                //.sorted(Post$.id)
                .map(PostResponse::new)
                .collect(Collectors.toList());

    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostResponse posts(
            @PathVariable long id
    ){

        Post post = jpaStreamer.stream(Post.class)
                .filter(Post$.id.equal(id))
                .findAny()
                .orElse(null);
        if(post == null)
            return null;

        return new PostResponse(post);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostResponse> users(){

        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(2));
        Date dateBeforeToday = Date.from(before);

        return jpaStreamer.stream(
                StreamConfiguration
                        .of(Post.class)
                        .joining(Post$.user)
        )
                .filter(Post$.content.in("Demo 1", "Demo 5")
                        .and(Post$.postedAt.between(dateBeforeToday, new Date()))
                )
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

}
