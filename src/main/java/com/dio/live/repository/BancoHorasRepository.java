package com.dio.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dio.live.model.BancoHoras;

@Repository
public interface BancoHorasRepository extends JpaRepository<BancoHoras, Long> {

}
