package com.baeldung.dependency.injectiontypes;

public class HotelService {

	private final RoomService roomService;

	public HotelService(RoomService roomService) {
		this.roomService = roomService;
	}
}
