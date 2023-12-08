import axios from 'axios'

var config = {
  url: '',
  method: '',
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json;charset=UTF-8'
  },
  data: null
}

export default {
  httpGet(url, callback, callbackError) {
    config.method = 'GET'
    config.url = url

    axios(config)
      .then((response) => {
        if (callback) callback(response)
      })
      .catch((error) => callbackError(error))
  },

  httpPost(url, data, callback, callbackError) {
    config.data = data
    config.method = 'POST'
    config.url = url

    axios(config)
      .then((response) => {
        if (callback) callback(response)
      })
      .catch((error) => callbackError(error))
  },

  httpPostPromise(url, data, callback, callbackError) {
    config.data = data
    config.method = 'POST'
    config.url = url

    new Promise((resolve, reject) => {
      axios(config)
        .then((response) => {
          if (callback) callback(response)
          resolve(response)
        })
        .catch((error) => {
          if (callbackError) callbackError(error)
          reject(error)
        })
    })
  }
}
