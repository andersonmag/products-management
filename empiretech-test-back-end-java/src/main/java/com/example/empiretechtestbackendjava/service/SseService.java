package com.example.empiretechtestbackendjava.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private final List<SseEmitter> listeners = new CopyOnWriteArrayList<>();

    public void addListener(SseEmitter listener) {
        listeners.add(listener);
        listener.onTimeout(() -> listeners.remove(listener));
        listener.onCompletion(() -> listeners.remove(listener));
    }

    public void sendEvent(String event) {
        for (SseEmitter listener : listeners) {
            try {
                listener.send(event);
            } catch (IOException e) {
                listener.complete();
                listeners.remove(listener);
            }
        }
    }
}
