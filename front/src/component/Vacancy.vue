<template>
  <b-container>
    <b-row align-h="between">
      <b-col cols="auto">
        <h3>{{ formatTitle }}</h3>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <h4>{{ formatEmployer }}</h4>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="auto">
        <h4>{{ formatSalary }}</h4>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <p v-html="formatDescription"></p>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="auto">
        <span class="text-muted"><em>{{ formatDateCreated }}</em></span>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="auto">
        <span class="text-muted"><em>{{ formatDatePublished }}</em></span>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import moment from 'moment'

import ApiVacancy from '../api/vacancy'
import BRow from 'bootstrap-vue/src/components/layout/row'
import BContainer from 'bootstrap-vue/src/components/layout/container'
import BCol from 'bootstrap-vue/src/components/layout/col'

export default {
  name: 'Vacancy',
  components: { BCol, BContainer, BRow },
  props: {
    repository: String,
    id: String,
    origin: String
  },
  data () {
    return {
      vacancy: {
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
  computed: {
    formatDateCreated () {
      let dateCreated = this.vacancy.dateCreated
      if (dateCreated) {
        return `Created: ${moment(dateCreated).format('MMMM Do YYYY [at] HH:mm:ss')}`
      }

      return 'Not created'
    },
    formatDatePublished () {
      let datePublished = this.vacancy.datePublished
      if (datePublished) {
        return `Published: ${moment(datePublished).format('MMMM Do YYYY [at] HH:mm:ss')}`
      }

      return 'Not published'
    },
    formatTitle () {
      return this.vacancy.title
    },
    formatDescription () {
      return this.vacancy.description
    },
    validSalary () {
      return this.vacancy.salary && (this.vacancy.salary.from || this.vacancy.salary.to) && this.vacancy.salary.currency
    },
    formatSalary () {
      if (this.vacancy.salary === null) {
        return ''
      }

      return `${this.formatSalaryRange} ${this.formatSalaryCurrency}`
    },
    formatSalaryRange () {
      let from = this.vacancy.salary.from
      let to = this.vacancy.salary.to

      switch (true) {
        case (from === to):
        case (from === null):
          return to
        case (to === null):
          return from
        default:
          return `${from} .. ${to}`
      }
    },
    formatSalaryCurrency () {
      return this.vacancy.salary.currency
    },
    validEmployer () {
      return this.vacancy.employer
    },
    formatEmployer () {
      let employer = this.vacancy.employer
      if (employer === null) {
        return 'Unspecified'
      }

      return employer.name
    }
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
