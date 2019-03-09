import { API } from './index'

export default {
  async index (params) {
    return API.get('/vacancy', { params: params })
  },
  async searchByTitle (params) {
    return API.get('/vacancy/search', { params: params })
  }
}
