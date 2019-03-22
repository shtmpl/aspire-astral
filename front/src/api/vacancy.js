import { API } from './index'

export default {
  async indexVacancies (repository, page, size) {
    return API.get(`/vacancy/${repository}/index`, { params: { page: page, size: size } })
  },
  async searchVacancies (repository, page, size, title) {
    return API.get(`/vacancy/${repository}/search`, { params: { page: page, size: size, 'title.like': title } })
  },
  async showVacancy (repository, id, origin) {
    return API.get(`/vacancy/${repository}/${id}`, { params: { origin: origin } })
  },
  async importVacancy (repository, id, origin) {
    return API.get(`/vacancy/${repository}/${id}/pull`, { params: { origin: origin } })
  },
  async deleteVacancy (repository, id, origin) {
    return API.delete(`/vacancy/${repository}/${id}`, { params: { origin: origin } })
  }
}
