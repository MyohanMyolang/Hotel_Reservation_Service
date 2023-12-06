package lv2.hotelService.room

import lv2.hotelService.user.User

data class RoomResInfo(val user: User, val checkIn: Int, val checkOut: Int, val resPrice:Int) {
}