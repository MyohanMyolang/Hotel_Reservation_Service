package lv4.hotelService.order

import lv4.hotelService.order.util.GetInputCheck
import lv4.hotelService.room.Room
import lv4.hotelService.room.RoomResInfo
import lv4.hotelService.user.User
import lv4.hotelService.util.InputUtil

object ResOrder : Order() {

    private fun payMoney(roomResInfo: RoomResInfo){
        roomResInfo.user.money -= roomResInfo.resPrice;
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

        checkIn = GetInputCheck.checkIn()
        if(checkIn == -1) return;

        checkOut = GetInputCheck.checkOut(checkIn);
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
                checkIn = GetInputCheck.checkIn()
                if(checkIn == -1) return;

                checkOut = GetInputCheck.checkOut(checkIn);
                if(checkOut == -1) return;

                roomResInfo = RoomResInfo(user, checkIn, checkOut, price)
            }
            payMoney(roomResInfo);
            println("예약이 완료되었습니다.");
        }
        else println("예약하지 않으셨습니다.");

    }
}