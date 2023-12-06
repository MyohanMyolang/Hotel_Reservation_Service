package lv2.hotelService.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtil {
    companion object{
        /**
         * @param format ex) yyyyMMdd
         * @param date: 날짜
         * @return 날짜 형식에 맞으면 true, 아니면 false를 반환한다.
         */
        fun checkIsDate(format: String, date:Int): Boolean{
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
        fun checkDate(date: Int): Boolean {
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
        fun compareDate(date: Int, target:Int): Boolean {
            val format = "yyyyMMdd"

            if(!checkIsDate(format, target)){
                println("${target}이 날짜 형식에 맞지 않습니다.");
                return false;
            }

            return date > target;
        }
    }
}