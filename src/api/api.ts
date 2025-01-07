import { ref } from 'vue';
import request from '../utils/request';

const hostname = (process.env.NODE_ENV === 'uat') ? '116.62.155.169' : 'localhost';
const jsonDbUrl = `http://${hostname}:3000`;
const springUrl = `http://localhost:8080`

export const getUserByUsername = async (username: string) => {
    username = username.toLowerCase();
    console.log("get user by username, ", username)
    return request({
        url: `${springUrl}/users/${username}`,
        method: 'GET',
    })
}


export const getSeatBook = async (dates)  => {
    console.log("dates:",dates)
    // const res = await request({
    //     url: springUrl+'/api/bookings/tableData' ,
    //     method: 'post',
    //     data: dates.value
    // })

    try {
        const res = await request({
            url: springUrl+'/api/bookings/tableData' ,
            method: 'post',
            data: dates.value
        });
        return res.data; // 成功返回数据
    } catch (error: any) {
        if (error.response) {
            // 捕获 HTTP 错误（如 400、404、500）
            console.error(`Error ${error.response.status}:`, error.response.data);
        } else if (error.request) {
            // 请求已发出，但未收到响应
            console.error("No response received:", error.request);
        } else {
            // 其他错误
            console.error("Error:", error.message);
        } // 抛出错误，让调用方处理
    }
}

interface seatBookByDateCard {
    id: string;
    seat: string;
    bookedUser: string;
}

export const getSeatBookByDate = async (date: string): Promise<seatBookByDateCard[]> => {
    const seatBookList = await getSeatBook(dates);

    const seatBookListByDate: seatBookByDateCard[] = [];
    // console.log("seatbook list: ", seatBookList.data)
    seatBookList.data.forEach(element => {
        // console.log("seatbook ele: ", element)
        let bookedUsername = '';
        if (element.dates.length > 0) {
            const findRec = element.dates.find(record => record.id === date);
            console.log("finded record: ", findRec)
            bookedUsername = findRec ? findRec.username : '';
        }
        seatBookListByDate.push({
            id: date,
            seat: element.id,
            bookedUser: bookedUsername,
        })
    });
    console.log("final seat book list by date: ", seatBookListByDate)
    return seatBookListByDate;
}

export const getSeats = async () => {
    return request({
        url: jsonDbUrl+'/seats',
        method: 'get'
    })
}

/**
 * 插入座位预订记录（通过后端校验）
 * 
 * @param id 座位的唯一标识
 * @param username 用户名
 * @param date 预订日期
 * @returns Promise<boolean> 表示预订是否成功
 */
export const insertSeatBook = async (id: string, username: string, date: string): Promise<boolean> => {
    console.log("prameters: ", id,username,date)
    console.log("url: ", jsonDbUrl+ '/book/' + id)
    let data = {};
    let dateList = [];

    try {
        // 调用后端接口进行预订
        const res = await request({
            url: `${springUrl}/api/bookings/book`,
            method: 'post',
            data: { seatId: id, username: username, bookingDate: date }
        });

        console.log("Booking successful: ", res.data);
        return true;
    } catch (error) {
        console.error("Booking failed: ", error.response?.data || error.message);
        return false;
    }
    request({
        url: jsonDbUrl + '/seat-book/' + id,
        method: 'get'
    }).then(res => {
        console.log("add seat before: ", res.data.dates)

        const record = {};
        record["id"] = date;
        record["date"] = date;
        record["username"] = username;
 
        dateList = res.data.dates;
        if (dateList.find( rec => rec.date === date)) {
            console.log(id, " is already booked")
            return false;
        }
        dateList.push(record);
        
        console.log("add seat after: ", dateList)

        console.log("fianl data: " , res.data)
        // data = res.data;
        // let url= jsonDbUrl + '/seat-book/' + id;
        let url = springUrl + '/book/' + id;
        request.patch(url, {dates:dateList});
        // console.log("dateList: ", JSON.stringify(dateList)); 
    });

    return Promise.resolve(true);

}

export const isSeatBookToday = (seat: string, username:string, date: string) => {
    // request({
    //     url: jsonDbUrl + '/seat-book/' + seat,
    //     method: 'get'
    // }).then(res => {
    //     const seatBook = res.data;
    //     console.log("response.data: ",res.data)
    //     return seatBook.dates.find(rec => rec.date === date);
    // })

    return request({
            url: jsonDbUrl + '/seat-book/' + seat,
            method: 'get'
        })
}

export const deleteSeatBook = async (id: string, date: string) => {
    return request({
        url: jsonDbUrl + `/seat-book/${id}`,
        method: 'GET'
    }).then(res => {
        const pacthUrl = `${jsonDbUrl}/seat-book/${id}`;
        const updatedDates = res.data.dates.filter((rec: {date: string})=> rec.date !== date);
        return request.patch(pacthUrl, {dates: updatedDates});
    })
}

export const validateLoginUser = (username:string) => {
    console.log(`login as ${username}`)
    username = username.toLowerCase();
    
    return request({
        url: jsonDbUrl + `/users/?username=${username}`,
        method: 'GET',
    })
}

export const addSeat = async (seat: {name:string, group:string}) => {
    console.log('add a seat with name and group');
    const addSeatUrl = `${jsonDbUrl}/seats`

    const res = await request({
        url: addSeatUrl,
        method: 'POST',
        data: seat
    })

    console.log("response of add a seat: ", res)
    console.log('add a empty seat booking record');
    const seatBook = {
        id: `${seat.name}-${seat.group}`,
        seatId: `${res.data.id}`,
        dates: [],
    };
    const res2 = await request ({
        url: `${jsonDbUrl}/seat-book`,
        method: 'POST',
        data: seatBook
    })
    console.log("response of add a seat booking record: ", res2)
    
    if (res && res2) {
        return true;
    }
    return false;
}

export const deleteSeat = async (id: string) => {
    const delSeatRes = await request({
        url: `${jsonDbUrl}/seats/${id}`,
        method: 'delete',
    });
    const seatId = `${delSeatRes.data.name}-${delSeatRes.data.group}`
    return await request({
        url: `${jsonDbUrl}/seat-book/${seatId}`,
        method: 'DELETE'
    })
};