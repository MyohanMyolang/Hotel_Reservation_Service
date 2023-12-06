package lv3.hotelService.room

import lv3.hotelService.user.User
import java.text.SimpleDateFormat

class RoomResInfoDto : Comparable<RoomResInfoDto>{
    val user: User;
    val checkIn: String;
    val checkOut: String;
    private val _checkIn: Int;
    val roomNum: Int;
    val index:Int;
    val resPrice:Int;

    constructor(roomResInfo: RoomResInfo, roomNum: Int, index:Int){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd");
        val toDate = SimpleDateFormat("yyyyMMdd");
        user = roomResInfo.user;
        _checkIn = roomResInfo.checkIn;
        checkIn = dateFormat.format(toDate.parse(roomResInfo.checkIn.toString()));
        checkOut = dateFormat.format(toDate.parse(roomResInfo.checkOut.toString()));
        this.roomNum = roomNum;
        this.index = index;
        this.resPrice = roomResInfo.resPrice;
    }

    override fun compareTo(other: RoomResInfoDto): Int {
        return this._checkIn.compareTo(other._checkIn);
    }

    override fun toString(): String {
        return "사용자 : ${user.name} | 방번호: $roomNum | 체크인: $checkIn | 체크아웃: $checkOut";
    }
}