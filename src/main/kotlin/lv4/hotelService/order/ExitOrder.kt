package lv4.hotelService.order

import lv4.hotelService.room.Room
import kotlin.system.exitProcess

object ExitOrder: Order() {
    override fun start(room: Room) {
        // 종료전 해야할 작업.
        exitProcess(0);
    }
}