import request from '../utils/request';

const jsonDbUrl = 'http://localhost:3000';

export const getSeatBook = () => {
    return request({
        url: jsonDbUrl+'/seat-book' ,
        method: 'get'
    })
}