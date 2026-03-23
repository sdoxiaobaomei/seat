import axios, { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

const service: AxiosInstance = axios.create({
    timeout: 5000
});

service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        return config;
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if ([201,200,204].includes(response.status)) {
            return response;
        } else {
            Promise.reject(new Error(`Unexpected response status: ${response.status}`));
        }
    },
    (error: AxiosError) => {
        console.log("Response error: ",error);
        return Promise.reject(error);
    }
);

export default service;
