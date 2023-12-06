package lv3.hotelService.room

import lv3.hotelService.user.User

data class RoomResInfo(val user: User, val checkIn: Int, val checkOut: Int, val resPrice:Int) {
}