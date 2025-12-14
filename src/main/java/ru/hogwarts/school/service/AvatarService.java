package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;

import java.io.IOException;

public interface AvatarService {
    void uploadAvatar(long studentId, MultipartFile file) throws IOException;

    Avatar getAvatar(long studentId);
}
