package mx.com.sunset.heimdall.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.sunset.heimdall.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
