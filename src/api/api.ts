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

export const insertSeatBook = () => {
    return request({
        url: jsonDbUrl + '/seat-book',
        method: 'put'
    })
}