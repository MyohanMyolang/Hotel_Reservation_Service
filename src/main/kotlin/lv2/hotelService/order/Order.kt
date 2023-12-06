package lv2.hotelService.order

import lv2.hotelService.room.Room

abstract class Order {
    abstract fun start(room: Room)

}