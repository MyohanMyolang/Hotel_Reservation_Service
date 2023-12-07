package lv4.hotelService.room

import lv4.hotelService.util.DateUtil
import java.util.*
import kotlin.collections.HashMap


class Room {
    /**
     * HashMap을 사용한 이유
     * 방 번호를 부여할 때, Array로 설정할 경우 안쓰는 방이 있을 때 손실되는 공간이 너무 많고, LinkedList의 경우 방을 찾는데 O(N)의 시간.
     *
     * value를 LinkedList로 사용한 이유
     * search 능력을 O(N)으로 성능이 떨어지되 추가 삭제가 많이 일어나므로.
     * ArrayList보다 메모리 관리 측면에서 효율적이라고 보았다. 추가 삭제가 빈번히 일어나며, 언제 추가 삭제 될지 모르는 상황이기 때문이다.
     * ArrayList를 사용한다 해도 O(N)의 로직을 구현해야 하는 부분에서 search 부분에서 손해를 보는 LinkedList의 특성상 가장 알맞다 생각한다.
     */
    private val room = HashMap<Int, LinkedList<RoomResInfo>>()



    /**
     * 방이 비어있는가 비어있지 않는가를 판단하는 method
     * 방 예약 가능 여부에 따라 ture Or false를 반환한다.
     * 방 자체가 존재하지 않을 경우 Null을 반환한다.
     * @param roomNum 방번호
     * @return Boolean?
     */
    fun checkHasRoom(roomNum: Int):Boolean {
        if(roomNum !in 100..< 1000) return false;

        return true;
    }

    /**
     * @return 예약 성공시 true 아니면 false
     */
    fun resRoom(roomNum: Int, roomResInfo: RoomResInfo): Boolean{
        var list: LinkedList<RoomResInfo>? = room[roomNum];
        if(list == null){
            list = LinkedList<RoomResInfo>();
            list.add(roomResInfo);
            room[roomNum] = list;

            return true;
        }

        if(!checkResRoom(list, roomResInfo.checkIn)) return false;

        list.add(roomResInfo);
        return true;
    }

    /**
     * 예약 가능한 상태인지 확인하는 함수
     * @param list 해당 방 번호의 예약 정보 list
     * @param checkIn 체크인 시간
     * @return 예약 가능할시 true 아니면 false
     */
    private fun checkResRoom(list: LinkedList<RoomResInfo>, checkIn: Int): Boolean{
        list.forEach{
            if(checkIn in it.checkIn..it.checkOut){
                println("이미 예약 되어 있습니다.");
                return false;
            }
        }
        return true;
    }

    /**
     * @return 모든 방의 예약 정보
     */
    fun getAllResInfo(): MutableList<RoomResInfoDto> {
        val result = mutableListOf<RoomResInfoDto>();
        room.keys.forEach(fun(roomNum: Int){
            room[roomNum]?.forEachIndexed{ index, it ->
                result.add(RoomResInfoDto(it, roomNum, index));
            }
        })

        return result;
    }

    fun getResInfoByRoomNum(roomNum: Int){

    }

    fun searchListByName(name: String): MutableList<RoomResInfoDto>{
        val result = mutableListOf<RoomResInfoDto>();
        room.keys.forEach(fun(roomNum: Int){
            room[roomNum]?.forEachIndexed{ index, it ->
                if(it.user.name == name) result.add(RoomResInfoDto(it, roomNum, index));
            }
        })

        return result;
    }

    /**
     * @return 성공하면 true 아니면 false
     */
    fun modifyRes(checkIn:Int, checkOut:Int, index:Int, roomNum: Int): Boolean {
        val list = room[roomNum];
        val olderItem = list!![index];
        if(!checkResRoom(list, checkIn)){
            return false;
        }

        list[index] = olderItem.copy(checkIn = checkIn, checkOut = checkOut);
        return true;
    }


    private fun refund(roomResInfo: RoomResInfo){
        val diff = DateUtil.getDiffFromCurDate(roomResInfo.checkIn);

        val per = when{
            diff >= 30 -> 100;
            diff >= 14 -> 80;
            diff >= 7 -> 50;
            diff >= 5 -> 30;
            diff >= 3 -> 0;
            else -> 0;
        }

        roomResInfo.user.money += roomResInfo.resPrice * per / 100;
        println("환불 완료.");
    }

    fun cancleRes(roomNum:Int, index:Int){
        val removedItem = room[roomNum]!!.removeAt(index);

        refund(removedItem);
    }
}