package com.es.phoneshop.web.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MapDosProtectionService implements DosProtectionService {
    private final static int MAX_REQUESTS_COUNT = 10;
    private Map<String, Integer> requestsCount = new ConcurrentHashMap<>();

    public static MapDosProtectionService getInstance() {
        return DosProtectionServiceHolder.dosProtectionService;
    }

    private MapDosProtectionService() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                requestsCount.clear();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 8000);
    }

    @Override
    public boolean allowed(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Integer count = requestsCount.get(ip);
        if (count == null) {
            count = 0;
        }
        count++;
        requestsCount.put(ip, count);

        return count <= MAX_REQUESTS_COUNT;
    }

    private static class DosProtectionServiceHolder {
        final static MapDosProtectionService dosProtectionService = new MapDosProtectionService();
    }
}
