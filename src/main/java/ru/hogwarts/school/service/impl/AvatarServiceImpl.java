package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private static final Logger logger= LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final String avatarsDir = "avatars";

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void uploadAvatar(long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method uploadAvatar for studentId={}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found for avatar upload, id={}", studentId);
                    return new RuntimeException("Student not found");
                });

        Files.createDirectories(Path.of(avatarsDir));
        logger.debug("Avatar directory checked/created: {}", avatarsDir);

        String ext = getExtension(file.getOriginalFilename());
        String filePath = avatarsDir + "/" + studentId + "." + ext;

        Path path = Path.of(filePath);
        Files.deleteIfExists(path);
        Files.copy(file.getInputStream(), path);

        logger.debug("Avatar file saved to {}", filePath);

        Avatar avatar = avatarRepository.findByStudentId(studentId)
                .orElseGet(() -> {
                    logger.debug("Creating new avatar entity for studentId={}", studentId);
                    return new Avatar();
                });

        avatar.setStudent(student);
        avatar.setFilePath(filePath);
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
        logger.info("Avatar successfully saved for studentId={}", studentId);
    }


    @Override
    public Avatar getAvatar(long studentId) {
        logger.info("Was invoked method getAvatar for studentId={}", studentId);

        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.error("Avatar not found for studentId={}", studentId);
                    return new RuntimeException("Avatar not found");
                });
    }


    @Override
    public Page<Avatar> getAvatars(int page, int size) {
        logger.info("Was invoked method getAvatars with page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Avatar> avatars = avatarRepository.findAll(pageable);

        logger.debug("Found {} avatars on page {}", avatars.getNumberOfElements(), page);
        return avatars;
    }

    private String getExtension(String filename) {
        logger.debug("Extracting file extension from filename={}", filename);
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

}

