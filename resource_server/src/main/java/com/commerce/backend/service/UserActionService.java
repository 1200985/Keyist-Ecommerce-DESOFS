package com.commerce.backend.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class UserActionService {

    private static final int MAX_CART_CONFIRMATIONS_PER_HOUR = 5;
    private final Map<String, Map<LocalDateTime, Integer>> userActions = new ConcurrentHashMap<>();

    public boolean canConfirmCart(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);
        Map<LocalDateTime, Integer> actions = userActions.getOrDefault(userId, new HashMap<>());
        
        actions.entrySet().removeIf(entry -> entry.getKey().isBefore(oneHourAgo));

        int count = actions.values().stream().mapToInt(Integer::intValue).sum();

        return count < MAX_CART_CONFIRMATIONS_PER_HOUR;
    }

    public void recordCartConfirmation(String userId) {
        LocalDateTime now = LocalDateTime.now();
        userActions.putIfAbsent(userId, new HashMap<>());
        Map<LocalDateTime, Integer> actions = userActions.get(userId);
        actions.merge(now, 1, Integer::sum);
    }
}
