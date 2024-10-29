package com.rupesh.assignment.lostfound.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.assignment.lostfound.domain.ClaimRecordDTO;
import com.rupesh.assignment.lostfound.domain.LostItemDTO;
import com.rupesh.assignment.lostfound.domain.LostandFoundUserDTO;
import com.rupesh.assignment.lostfound.service.ClaimRecordService;
import com.rupesh.assignment.lostfound.service.LostItemService;
import com.rupesh.assignment.lostfound.service.LostItemServiceImpl;
import com.rupesh.assignment.lostfound.service.LostandFoundUserService;

/**
 * Handles user-related operations.
 * 
 * @author Rupesh
 *
 */
@RestController
@RequestMapping("/lost-found")
public class UserController {

	private LostItemService lostItemService;

	private ClaimRecordService claimRecordService;

	private LostandFoundUserService userService;

	@Autowired
	public UserController(LostItemServiceImpl lostItemService, ClaimRecordService claimRecordService,
			LostandFoundUserService userService) {
		this.lostItemService = lostItemService;
		this.claimRecordService = claimRecordService;
		this.userService = userService;
	}

	/**
	 * Retrieves a list of all lost items.
	 * 
	 * @return message success or error
	 */
	@GetMapping("/lost-items")
	public ResponseEntity<List<LostItemDTO>> getAllLostItems() {
		return ResponseEntity.ok(lostItemService.getAllLostItems());
	}

	/**
	 * sends a claim request with parameters of userID, ItemID, and Quantity
	 * 
	 * @return message success or error
	 */
	@PostMapping("/claim-item")
	public ResponseEntity<String> claimLostItem(@RequestParam Long userId, @RequestParam String itemId,
			@RequestParam int quantity) {
		return ResponseEntity.ok(claimRecordService.claimLostItem(userId, itemId, quantity));
	}

	/**
	 * Retrieves a list of all users.
	 * 
	 * @return a list of users
	 */

	@GetMapping("/all-users")
	public List<LostandFoundUserDTO> getAllUser() {
		return userService.getAllUsers();
	}

	/**
	 * Retrieves a list of all claimed items.
	 * 
	 * @return success or error message
	 */
	
	@GetMapping("/claimed-items")
	public ResponseEntity<List<ClaimRecordDTO>> getAllClaimedItems() {
		return ResponseEntity.ok(claimRecordService.getAllClaimedItems());
	}

	/**
	 * This method will upload the CSV file in which the data of lost items will be present.
	 * Also handles CSV related errors 
	 * @param file
	 * @return error or success message.
	 */
	@PostMapping("/upload-csv")
	public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please upload a valid CSV file.");
		}
		try {
			lostItemService.processCSVFile(file);
			return ResponseEntity.ok("CSV file uploaded and data inserted successfully.");
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Error while processing the CSV file.");
		}
	}

}
