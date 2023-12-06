package lv4.hotelService.util

import java.lang.NumberFormatException

class InputUtil {
    companion object {
        /**
         * readln으로 입력 받은 값을 숫자로 변환 시켜 반환하는 함수
         * @return 입력 받은 값 반환, 숫자가 아닐 경우 null을 반환하며 다른 에러가 날 경우 -1을 반환한다.
         */
        private fun readlnConverToInt(): Int? {
            try {
                return readln().toInt();
            } catch (e: NumberFormatException) {
                println("숫자를 입력하여 주십시오.")
                return null;
            } catch (e: Exception) {
                return -1;
            }
        }

        /**
         * @param text 안내글자
         * @param action 실행시킬 로직을 담은 함수 성공시 ture 실패시 false를 반환하는 함수를 전달해 주십시오.
         * @param action.Int console에서 입력 받은 값이 들어가게 됩니다.
         * @return 입력받은 숫자 또는 취소용 -1
         */
        fun getInputNumber(text: String, action: (Int) -> Boolean): Int {
            var result: Int?;
            while (true) {
                print("$text \n0보다 작은 값을 입력할 시 초기 화면으로 돌아갑니다. \n입력 : ");
                result = readlnConverToInt();
                if (result == null) continue;
                else if (result < 0) break;
                if (!action(result)) continue;

                break;
            }

            return result ?: -1;
        }
    }
}