package ru.bellintegrator.practice.user.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.practice.user.view.ActivationView;

import java.util.List;

public interface ActivationController {
    String checkActivationCode(@RequestParam String code);
}
