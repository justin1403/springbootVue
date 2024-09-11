import axios from 'axios'
import Vue from "vue";

const request = axios.create({
    baseURL:'http://localhost:9090',
    timeout:1000
})
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):null
    if(user){
        config.headers['token'] =user.token;    // 設置請求頭，用戶管理頁，查詢標頭 token 值
    }
    return config
},error => {
    return Promise.reject(error)
});

request.interceptors.response.use(
    response => {
        let res = response.data;
        if (response.config.responseType === 'blob') {
            return res
        }
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code==='401'){
            Vue.prototype.$message.error(res.msg)
        }
        return res;
    },
        error => {
        console.log('err'+error)    // for debug
        return Promise.reject(error)
    }
)

export default request











