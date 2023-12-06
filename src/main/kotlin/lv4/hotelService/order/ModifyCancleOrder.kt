package lv4.hotelService.order

import lv4.hotelService.order.util.GetInputCheck
import lv4.hotelService.room.Room
import lv4.hotelService.util.InputUtil

object ModifyCancleOrder: Order() {
    override fun start(room: Room) {
        print("예약자명을 입력하여 주십시오. \n입력 : ");
        val name = readln();
        val searchList = room.searchListByName(name);
        if(searchList.size == 0){
            println("예약된 사용자를 찾을 수 없습니다.");
            return;
        }
        searchList.forEachIndexed{index, roomResInfoDto ->
            println("${index+1}. ${roomResInfoDto.toString()}");
        }
        var index = InputUtil.getInputNumber("해당하는 번호를 입력하여 주십시오", fun(value:Int):Boolean{
            if(value !in 1..searchList.size){
                println("해당하는 번호가 없습니다.")
                return false;
            }
            return true;
        })
        if(index == -1) return;

        println("[ 취소 유의사항 ]")
        println("체크인 3일 이전 취소 예약금 환불 불가")
        println("체크인 5일 이전 취소 예약금의 30% 환불")
        println("체크인 7일 이전 취소 예약금의 50% 환불")
        println("체크인 14일 이전 취소 예약금의 80% 환불")
        println("체크인 30일 이전 취소 예약금의 100% 환불")
        val order = InputUtil.getInputNumber("해당 예약을 어떻게 하시겠습니까. \n1. 변경 2.취소", fun(value: Int): Boolean {
            if (value !in 1..2){
                println("1 또는 2를 입력하여 주십시오.")
                return false;
            }
            return true;
        })
        if(order == -1) return;

        val data = searchList[index-1];

        when(order){
            1 -> {
                var checkIn: Int;
                var checkOut: Int;
                while(true){
                    checkIn = GetInputCheck.checkIn();
                    checkOut = GetInputCheck.checkOut(checkIn);
                    if(room.modifyRes(checkIn, checkOut, data.index, data.roomNum))
                        break;
                }
                println("수정이 완료되었습니다.");
            }
            2 -> {
                room.cancleRes(data.roomNum, data.index)
                println("취소가 완료되었습니다.");
            }
        }
    }
}