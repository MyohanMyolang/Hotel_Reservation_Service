package lv4.hotelService.order

import lv4.hotelService.room.Room

abstract class Order {
    abstract fun start(room: Room)

}