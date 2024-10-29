package com.rupesh.assignment.lostfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupesh.assignment.lostfound.domain.ClaimRecord;
import com.rupesh.assignment.lostfound.domain.LostItem;

@Repository
public interface ClaimRecordRepository extends JpaRepository<ClaimRecord, Long> {

  boolean existsByUserIdAndLostItem(Long userID, LostItem lostItem);

  boolean existsByLostItemAndUserIdNot(LostItem lostItem, Long userId);

  List<ClaimRecord> findByLostItem(LostItem lostItem);
}
