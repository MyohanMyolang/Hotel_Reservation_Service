package lv4.hotelService.order.util

import lv4.hotelService.util.DateUtil
import lv4.hotelService.util.InputUtil

class GetInputCheck {
    companion object{
        fun checkIn():Int{
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

        fun checkOut(checkIn: Int): Int{
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
    }
}