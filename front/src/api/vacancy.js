import { API } from './index'

export default {
  async indexVacancies (page, size, origin) {
    return API.get('/vacancy/index', { params: { page: page, size: size, origin: origin } })
  },
  async searchVacancies (page, size, origin, title) {
    return API.get('/vacancy/search', { params: { page: page, size: size, origin: origin, 'title.like': title } })
  },
  async importVacancy (vacancy) {
    return API.get(`/vacancy/${vacancy.id}/acquire`, { params: { origin: 'remote' } })
  }
}
