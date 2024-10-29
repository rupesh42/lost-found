package com.rupesh.assignment.lostfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupesh.assignment.lostfound.domain.ClaimRecord;
import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.domain.LostandFoundUser;

@Repository
public interface ClaimRecordRepository extends JpaRepository<ClaimRecord, Long> {

	boolean existsByUserAndLostItem(LostandFoundUser user, LostItem lostItem);
	
	List<ClaimRecord> findByLostItem(LostItem lostItem);
}
