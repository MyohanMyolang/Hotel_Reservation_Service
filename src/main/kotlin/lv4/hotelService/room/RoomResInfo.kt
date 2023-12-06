package lv4.hotelService.room

import lv4.hotelService.user.User

data class RoomResInfo(val user: User, val checkIn: Int, val checkOut: Int, val resPrice:Int) {
}