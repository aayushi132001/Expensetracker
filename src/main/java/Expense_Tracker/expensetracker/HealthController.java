package Expense_Tracker.expensetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller
 * This endpoint can be called by Render's cron job to keep the app alive.
 * Free tier Render apps spin down after 15 minutes of inactivity.
 */
@RestController
@CrossOrigin(origins = "*")
public class HealthController {

    @Autowired
    private ExpenseRepository repository;

    /**
     * Simple health check - just returns OK status
     * Use this for basic ping: GET /health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", Instant.now().toString());
        response.put("service", "expense-tracker");
        return ResponseEntity.ok(response);
    }

    /**
     * Detailed health check - includes database connectivity
     * Use this for full health: GET /health/detailed
     */
    @GetMapping("/health/detailed")
    public ResponseEntity<Map<String, Object>> detailedHealthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", Instant.now().toString());
        response.put("service", "expense-tracker");
        
        // Check database connectivity
        try {
            long count = repository.count();
            response.put("status", "UP");
            response.put("database", "CONNECTED");
            response.put("expenseCount", count);
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("database", "DISCONNECTED");
            response.put("error", e.getMessage());
            return ResponseEntity.status(503).body(response);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Simple ping endpoint - minimal response
     * Use this for cron jobs: GET /ping
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
