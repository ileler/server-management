import axios from 'axios';

const HTTP = axios.create({
    baseURL: process.env.VUE_APP_CTX || '/',
    timeout: 3000,
    headers: {}
});

export default {
    add: (path, item, callback) => {
        let result;
        HTTP.post(path, item)
            .then((response) => {
                console.log(response);
                result = response.data;
            })
            .catch((error) => {
                console.log(error);
                result = null;
            })
            .then(() => {
                // always executed
                callback(result);
            });
    },
    del: (path, callback) => {
        let result;
        HTTP.delete(path)
            .then((response) => {
                console.log(response);
                result = response.data;
            })
            .catch((error) => {
                console.log(error);
                result = null;
            })
            .then(() => {
                // always executed
                callback(result);
            });
    },
    mod: (path, item, callback) => {
        let result;
        HTTP.put(path, item)
            .then((response) => {
                console.log(response);
                result = response.data;
            })
            .catch((error) => {
                console.log(error);
                result = null;
            })
            .then(() => {
                // always executed
                callback(result);
            });
    },
    get: (path, callback) => {
        let result;
        let loadStart = Date.now();
        HTTP.get(path)
            .then((response) => {
                console.log(response);
                result = response.data;
            })
            .catch((error) => {
                console.log(error);
                result = null;
            })
            .then(() => {
                // always executed
                setTimeout(() => {
                    callback(result);
                }, (Date.now() - loadStart) > (1.5 * 1000) ? 0 : 800);    //避免过快更新UI导致用户视觉感觉不舒服(像闪屏)
            });
    },
};