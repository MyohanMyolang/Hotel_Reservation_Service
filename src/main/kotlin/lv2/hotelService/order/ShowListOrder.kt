package lv2.hotelService.order

import lv2.hotelService.room.Room
import lv2.hotelService.room.RoomResInfoDto

object ShowListOrder : Order() {

    override fun start(room: Room) {
        val list = room.getAllResInfo();
        if (list.size == 0){
            println("예약 인원이 없습니다.");
            return;
        }
        list.forEachIndexed { index, roomResInfoDto ->
            println("${index+1}. $roomResInfoDto");
        }
    }
}