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


export const getSeatBook = () => {
    return request({
        url: jsonDbUrl+'/seat-book' ,
        method: 'get'
    })
}

interface seatBookByDateCard {
    id: string;
    seat: string;
    bookedUser: string;
}

export const getSeatBookByDate = async (date: string): Promise<seatBookByDateCard[]> => {
    const seatBookList = await getSeatBook();

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

export const insertSeatBook = (id: string, username: string, date: string): Promise<boolean> => {
    console.log("prameters: ", id,username,date)
    console.log("url: ", jsonDbUrl+ '/seat-book/' + id)
    let data = {};
    let dateList = [];
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
        let url= jsonDbUrl + '/seat-book/' + id;
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