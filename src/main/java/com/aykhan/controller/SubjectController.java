package com.aykhan.controller;

import com.aykhan.entities.MyUser;
import com.aykhan.entities.Subject;
import com.aykhan.services.implementations.MyUserDetailsService;
import com.aykhan.services.implementations.SubjectService;
import com.aykhan.util.AuthAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/subject**")
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public SubjectController(SubjectService subjectService,
                             MyUserDetailsService userDetailsService) {
        this.subjectService = subjectService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/e")
    boolean doesExist(@RequestParam("subject") String subject) {
        return subjectService.isPresent(subject);
    }

    @GetMapping("")
    Stream<String> getMine() {
        return Optional.ofNullable(userDetailsService.getByUsername(AuthAccess.currentUser().getUsername()))
                .map(subjectService::getAllByUser)
                .map(
                        subjects -> subjects.stream().map(Subject::getName)
                )
                .orElse(null);
    }

    @PostMapping("")
    void addSubject(@RequestBody String subject) {
        log.info(String.format("received info %s\n", subject));
        MyUser myUser = userDetailsService.getByUsername(AuthAccess.currentUser().getUsername());
        subjectService.saveOne(new Subject(-1, subject, myUser));
    }
}
