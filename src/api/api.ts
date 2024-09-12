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

export const insertSeatBook = (id, username, date) => {
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
        record["date"] = date;
        record["username"] = username;
 
        dateList = res.data.dates;
        dateList.push(record);
        
        console.log("add seat after: ", dateList)

        console.log("fianl data: " , res.data)
        // data = res.data;
        let url= jsonDbUrl + '/seat-book/' + id;
        request.patch(url, {dates:dateList});
        // console.log("dateList: ", JSON.stringify(dateList)); 
    });
    // ({
        
    //     method: 'PATCH',
    //     data: dateList
    // })


    
    return true;

    // return request({
    //     url: jsonDbUrl + '/seat-book',
    //     method: 'put'
    // })
}