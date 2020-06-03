package com.sweater.sweater.controller;

import com.sweater.sweater.domain.Message;
import com.sweater.sweater.domain.User;
import com.sweater.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.put("messages", messages);
        model.put("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      @RequestParam MultipartFile multipartFile,
                      Map<String, Object> model) throws IOException {
        Message message = new Message(text, tag, user);
        if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuid = UUID.randomUUID().toString();
            String uuidFile = uuid + multipartFile.getOriginalFilename();

            multipartFile.transferTo( new File(uploadPath + "/" + uuidFile));

            message.setFilename(uuidFile);
        }

        messageRepository.save(message);

        Iterable<Message> all = messageRepository.findAll();
        model.put("messages", all);
        return "main";
    }
}