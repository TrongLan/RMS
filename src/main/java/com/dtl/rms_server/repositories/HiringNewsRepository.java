package com.dtl.rms_server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtl.rms_server.models.HiringNews;

public interface HiringNewsRepository extends JpaRepository<HiringNews, UUID> {

}
