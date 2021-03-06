package com.aykhan.services.implementations;

import com.aykhan.entities.MyUser;
import com.aykhan.entities.Subject;
import com.aykhan.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public int saveOne(Subject sub) {
        return subjectRepository.save(sub).getId();
    }

    public Subject getByName(String name) {
        return subjectRepository.getByName(name);
    }

    public boolean isPresent(String name) {
        return subjectRepository.existsByName(name);
    }

    public List<Subject> getAllByUser(MyUser user) {
        return subjectRepository.getAllByOwner(user);
    }
}
