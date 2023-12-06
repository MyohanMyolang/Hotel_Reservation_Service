package lv2.hotelService.order

import lv2.hotelService.room.Room
import lv2.hotelService.room.RoomResInfo
import lv2.hotelService.user.User
import lv2.hotelService.util.DateUtil
import lv2.hotelService.util.InputUtil

object ResOrder : Order() {

    private fun payMoney(roomResInfo: RoomResInfo){
        roomResInfo.user.money -= roomResInfo.resPrice;
    }

    private fun getInputCheckIn():Int{
        return InputUtil.getInputNumber("체크인 날짜를 입력하여 주십시오. / 형식 - 20230101", fun(value: Int): Boolean{
            return when(DateUtil.checkDate(value)){
                true -> true
                else -> {
                    println("현제 날짜보다 이전의 날짜는 입력하실 수 없습니다.");
                    return false;
                }
            };
        })
    }

    private fun getInputCheckOut(checkIn: Int): Int{
        return InputUtil.getInputNumber("체크아웃 날짜를 입력하여 주십시오. / 형식 - 20230101", fun(value: Int): Boolean{
            return when(DateUtil.compareDate(value, checkIn)){
                true -> true
                else -> {
                    println("체크인 날짜보다 이전 날짜는 입력하실 수 없습니다.");
                    return false;
                }
            };
        })
    }

    override fun start(room: Room) {
        var roomNum:Int;
        print("예약자분의 성함을 입력하여 주십시오. \n입력 : ");
        val user = User(readln());
        var checkIn: Int;
        var checkOut: Int;

        roomNum = InputUtil.getInputNumber("방 번호를 입력하여 주십시오", fun(value: Int): Boolean{
            val hasRoom: Boolean = room.checkHasRoom(value);
            if(!hasRoom){
                print("100-999 사이의 방 번호를 입력하여 주십시오.");
                return false;
            }
            return true;
        })
        if(roomNum == -1) return;

        checkIn = getInputCheckIn()
        if(checkIn == -1) return;

        checkOut = getInputCheckOut(checkIn);
        if(checkOut == -1) return;

        var price = (0..500000).random();
        var ans:String = "";
        while (true){
            println("예약금은 ${price}원 입니다. 예약하시겠습니까? y or n")
            print("입력 : ");
            ans = readln();
            if(ans == "y" || ans == "n") break;
        }

        if(ans == "y") {
            var roomResInfo: RoomResInfo = RoomResInfo(user, checkIn, checkOut, price);
            var isFail = true;
            while(isFail) {
                if(room.resRoom(roomNum, roomResInfo)){
                    isFail = false;
                    continue;
                }
                checkIn = getInputCheckIn()
                if(checkIn == -1) return;

                checkOut = getInputCheckOut(checkIn);
                if(checkOut == -1) return;

                roomResInfo = RoomResInfo(user, checkIn, checkOut, price)
            }
            payMoney(roomResInfo);
            println("예약이 완료되었습니다.");
        }
        else println("예약하지 않으셨습니다.");

    }
}