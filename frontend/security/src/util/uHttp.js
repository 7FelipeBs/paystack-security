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
  httpGet(url, callback) {
    config.method = 'GET'
    config.url = url

    axios(config)
      .then((response) => {
        if (callback) callback(response)
      })
      .catch((error) => console.log(error))
  },

  httpPost(url, data, callback) {
    config.data = data
    config.method = 'POST'
    config.url = url

    axios(config)
      .then((response) => {
        if (callback) callback(response)
      })
      .catch((error) => console.log(error))
  }
}
