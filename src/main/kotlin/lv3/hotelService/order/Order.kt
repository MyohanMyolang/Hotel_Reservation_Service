package lv3.hotelService.order

import lv3.hotelService.room.Room

abstract class Order {
    abstract fun start(room: Room)

}