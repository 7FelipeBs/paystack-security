import axios from 'axios'
import uApi from './uApi'

var config = {
  url: '',
  method: 'GET',
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json;charset=UTF-8'
  },
  data: null
}

export default {
  httpGet(url, data) {
    config.data = data
    config.url = uApi.CONTENT_USER

    console.log(config)
    axios(config)
      .then((response) => {
        console.log(response)
      })
      .catch((error) => console.log(error))
  }
}
