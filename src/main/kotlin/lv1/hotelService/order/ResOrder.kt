package lv1.hotelService.order

import lv1.hotelService.Room
import lv1.hotelService.RoomResInfo
import lv1.hotelService.User
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ResOrder : Order() {

    private fun payMoney(roomResInfo: RoomResInfo){
        roomResInfo.user.money -= roomResInfo.resPrice;
    }

    /**
     * @param format ex) yyyyMMdd
     * @param date: 날짜
     * @return 날짜 형식에 맞으면 true, 아니면 false를 반환한다.
     */
    private fun checkIsDate(format: String, date:Int): Boolean{
        val checkIsDate = SimpleDateFormat(format);
        checkIsDate.isLenient = false;
        return try{
                checkIsDate.parse(date.toString());
                true;
            } catch (e : ParseException) {
                false
            }
    }


    /**
     * @param date 기준 날짜
     * @return 기준 날짜가 현재 날짜보다 이후라면 true 아니면 false
     */
    private fun checkDate(date: Int): Boolean {
        val format = "yyyyMMdd"
        val cur = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern(format);
        val formatted = cur.format(formatter);

        if(!checkIsDate(format, date)){
            println("${date}이 날짜 형식에 맞지 않습니다.");
            return false;
        }


        return date >= formatted.toInt();
    }

    /**
     * @param date 기준 날짜
     * @param target 비교할 날짜
     * @return 기준 날짜가 비교할 날짜보다 이후라면 true 아니면 false
     */
    private fun compareDate(date: Int, target:Int): Boolean {
        val format = "yyyyMMdd"
        if(!checkIsDate(format, target)){
            println("${target}이 날짜 형식에 맞지 않습니다.");
            return false;
        }
        return date > target;
    }

    /**
     * readln으로 입력 받은 값을 숫자로 변환 시켜 반환하는 함수
     * @return 입력 받은 값 반환, 숫자가 아닐 경우 null을 반환하며 다른 에러가 날 경우 -1을 반환한다.
     */
    private fun readlnConverToInt(): Int?{
        try {
            return readln().toInt();
        }
        catch (e: NumberFormatException){
            println("숫자를 입력하여 주십시오.")
            return null;
        }
        catch(e: Exception){
            return -1;
        }
    }

    /**
     * @param text 안내글자
     * @param action 실행시킬 로직을 담은 함수 성공시 ture 실패시 false를 반환하는 함수를 전달해 주십시오.
     * @param action.Int console에서 입력 받은 값이 들어가게 됩니다.
     */
    private fun getInputNumber(text:String, action:(Int) -> Boolean): Int{
        var result: Int?;
        while (true){
            print("$text \n0보다 작은 값을 입력할 시 초기 화면으로 돌아갑니다. \n입력 : ");
            result = readlnConverToInt();
            if(result == null) continue;
            else if(result < 0) break;
            if(!action(result)) continue;

            break;
        }

        return result ?: -1;
    }


    override fun start(room: Room) {
        var roomNum:Int;
        print("예약자분의 성함을 입력하여 주십시오. \n입력 : ");
        val user = User(readln());
        var checkIn: Int;
        var checkOut: Int;

        roomNum = getInputNumber("방 번호를 입력하여 주십시오", fun(value: Int): Boolean{
            val hasRoom: Boolean = room.checkHasRoom(value);
            if(!hasRoom){
                print("100-999 사이의 방 번호를 입력하여 주십시오.");
                return false;
            }
            return true;
        })
        if(roomNum == -1) return;

        checkIn = getInputNumber("체크인 날짜를 입력하여 주십시오. / 형식 - 20230101", fun(value: Int): Boolean{
            return when(checkDate(value)){
                true -> true
                else -> {
                    println("현제 날짜보다 이전의 날짜는 입력하실 수 없습니다.");
                    return false;
                }
            };
        })
        if(checkIn == -1) return;

        checkOut = getInputNumber("체크아웃 날짜를 입력하여 주십시오. / 형식 - 20230101", fun(value: Int): Boolean{
            return when(compareDate(value, checkIn)){
                true -> true
                else -> {
                    println("체크인 날짜보다 이전 날짜는 입력하실 수 없습니다.");
                    return false;
                }
            };
        })
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
            println("예약이 완료되었습니다.");
            val roomResInfo = RoomResInfo(user, checkIn, checkOut, price);
            room.resRoom(roomNum, roomResInfo);
            payMoney(roomResInfo);
        }
        else println("예약하지 않으셨습니다.");

    }
}