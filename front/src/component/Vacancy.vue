<template>
  <div>
    <h2>{{ vacancy.title }}</h2>
    <p v-html="vacancy.description"></p>
  </div>
</template>

<script>
import ApiVacancy from '../api/vacancy'

export default {
  name: 'Vacancy',
  props: {
    repository: String,
    id: String,
    origin: String
  },
  data () {
    return {
      vacancy: {
        id: '',
        origin: '',
        dateCreated: '',
        datePublished: '',
        title: '',
        description: '',
        salary: {},
        employment: '',
        employer: '',
        contacts: []
      }
    }
  },
  created () {
    console.log(`Fetching vacancy from ${this.repository} for ${this.id}:${this.origin}`)
    this.showVacancy(this.repository, this.id, this.origin)
  },
  methods: {
    showVacancy (repository, id, origin) {
      ApiVacancy.showVacancy(repository, id, origin).then(response => {
        this.vacancy = response.data
      }).catch(error => {
        this.$emit('error', error)
      })
    }
  }
}
</script>

<style scoped>

</style>
