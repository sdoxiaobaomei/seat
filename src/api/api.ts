import request from '../utils/request';

const jsonDbUrl = 'http://116.62.155.169:3000';

export const getSeatBook = () => {
    return request({
        url: jsonDbUrl+'/seat-book' ,
        method: 'get'
    })
}