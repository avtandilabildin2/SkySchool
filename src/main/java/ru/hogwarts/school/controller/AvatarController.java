package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;


    @PostMapping("/{id}/avatar")
    public ResponseEntity<Void> uploadAvatar(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        avatarService.uploadAvatar(id, file);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/avatar/db")
    public ResponseEntity<byte[]> getAvatarFromDB(
            @PathVariable long id
    ) {
        Avatar avatar = avatarService.getAvatar(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(avatar.getFileSize())
                .body(avatar.getData());
    }

    @GetMapping("/{id}/avatar/file")
    public ResponseEntity<byte[]> getAvatarFromFile(
            @PathVariable long id
    ) throws IOException {

        Avatar avatar = avatarService.getAvatar(id);

        Path path = Path.of(avatar.getFilePath());
        byte[] data = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(data.length)
                .body(data);
    }
    @GetMapping("/page-avatars")
    public Page<Avatar> getAvatars(
            @RequestParam int page,
            @RequestParam int size
    ){
        return avatarService.getAvatars(page,size);
    }
}

