package lv3.hotelService.order

import lv3.hotelService.room.Room
import lv3.hotelService.util.InputUtil

object AmountListOrder: Order() {
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
        val item = searchList.get(index-1);
        println("1. 초기 금액으로 ${item.resPrice + item.user.money}원이 입금되었습니다.")
        println("2. 예약금으로 ${item.resPrice}원이 출금되었습니다.");
    }
}