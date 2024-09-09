import axios from 'axios'
import Vue from "vue";

const request = axios.create({
    baseURL:'http://localhost:9090', // 注意！！ 這裡是全局統一加上了 '/api' 前綴，也就是說所有接口都會加上 '/api' 前綴在，頁面裡面寫接口的時候就不要加 '/api' 了，否則會出現二個 '/api'，類似 '/api/api/user' 這樣的報錯，切記！！！
    timeout:1000
})

// request 攔截器
// 可以自請求發送前對請求做一些處理
// 比如光一加上 token，對請求參數統一加密
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

// response 攔截器
// 可以在接口響應後統一處理結果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服務端返回的字符串數據
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 無權限時，會告知
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











