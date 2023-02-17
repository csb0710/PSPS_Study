package com.study.psver1.repository;

import com.study.psver1.entitiy.Boardpj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Boardpj, Integer> {
    Page<Boardpj> findByTitleContaining(String searchKeyword, Pageable pageable);
}
