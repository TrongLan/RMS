package com.dtl.rms_server.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtl.rms_server.models.Category;
import com.dtl.rms_server.models.HiringNews;

public interface HiringNewsRepository extends JpaRepository<HiringNews, UUID> {
	Optional<HiringNews> findByIdAndIsActive(UUID id, int isActive);
	List<HiringNews> findAllByCategoryAndIsActiveOrderByDueDateDesc(
			Category category, int isActive);
	List<HiringNews> findAllByDueDateAndIsActive(LocalDate dueDate,
			int isActive);
}
