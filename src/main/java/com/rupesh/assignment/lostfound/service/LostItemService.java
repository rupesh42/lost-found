package com.rupesh.assignment.lostfound.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rupesh.assignment.lostfound.domain.LostItemDTO;

/**
 * This service fetches all the CSV records and stores it in entity
 * Also will server all the records if asked for.
 * @author Rupesh
 *
 */
public interface LostItemService {

	public List<LostItemDTO> getAllLostItems();
	
	public void processCSVFile(MultipartFile file) throws IOException;
	
}
