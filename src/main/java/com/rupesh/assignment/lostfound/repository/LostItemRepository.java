package com.rupesh.assignment.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupesh.assignment.lostfound.domain.LostItem;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, String> {

}

