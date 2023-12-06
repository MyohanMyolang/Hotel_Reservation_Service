package lv1.hotelService.order

import lv1.hotelService.Room
import java.lang.Exception

abstract class Order {
    abstract fun start(room: Room)

}