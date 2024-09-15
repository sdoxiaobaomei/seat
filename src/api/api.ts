import { ref } from 'vue';
import request from '../utils/request';

const jsonDbUrl = 'http://localhost:3000';

export const getSeatBook = () => {
    return request({
        url: jsonDbUrl+'/seat-book' ,
        method: 'get'
    })
}

export const getSeats = () => {
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
            url: jsonDbUrl + '/seat-book?seat=' + seat,
            method: 'get'
        })
}

export const deleteSeatBook = (id: string, date: string) => {
    request({
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
    
    return request({
        url: jsonDbUrl + `/users/?username=${username}`,
        method: 'GET'
    })
}