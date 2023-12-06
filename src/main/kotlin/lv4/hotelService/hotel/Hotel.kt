package lv4.hotelService.hotel

import lv4.hotelService.order.*
import lv4.hotelService.room.Room
import java.lang.Exception

class Hotel {

    private val room: Room = Room();


    private fun showServiceMenu(){
        println("호텔예약 프로그램 입니다.");
        println("[메뉴]")
        println("1. 방예약                 2. 예약목록 출력")
        println("3. 예약목록 (정렬)         4. 시스템 종료")
        println("5. 금액 입출금 내역 목록    6. 예약 변경/취소")
    }

    private fun getOrder(): Order? {
        var result: Int = -1;
        try {
            print("입력 : ")
            val line = readln();
            if(line.trim() != "")
                result = line.toInt();
        }
        catch (e: Exception){
            println("잘못된 값")
            println(e.stackTrace)
        }
        return checkOrder(result);
    }

    private fun checkOrder(order: Int): Order? {
        return when(order){
            1 -> ResOrder;
            2 -> ShowListOrder
            3 -> ShowSortListOrder
            4 -> ExitOrder;
            5 -> AmountListOrder
            6 -> ModifyCancleOrder
            -1 -> {
                println("값이 입력되지 않았습니다.")
                null
            };
            else -> {
                println("1-6 사이의 값을 입력하여 주십시오.")
                null
            };
        }
    }

    fun startService(){
        while (true) {
            showServiceMenu();
            val order = getOrder() ?: continue;
            order.start(room);
        }
    }
}