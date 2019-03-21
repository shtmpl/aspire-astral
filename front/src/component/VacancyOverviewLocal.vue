<template>
  <div>
    <b-card no-body>
      <b-card-body>
        <b-row align-h="between">
          <b-col cols="auto">
            <b-card-title>
              {{ formatTitle }}
            </b-card-title>
          </b-col>
          <b-col cols="auto">
            <b-card-title v-show="validSalary">
              {{ formatSalary }}
            </b-card-title>
          </b-col>
        </b-row>
        <b-card-text v-show="validEmployer">
          {{ formatEmployer }}
        </b-card-text>
        <b-card-sub-title>
          <small class="text-muted"><em>{{ formatDatePublished }}</em></small>
        </b-card-sub-title>
      </b-card-body>

      <b-card-body>
        <b-row align-h="end">
          <b-col cols="auto">
            <b-button variant="outline-danger"
                      v-on:click="$emit('vacancy-delete', { id: id, origin: origin })"> <!--FIXME-->
              <span><i class="fas fa-trash"></i></span>
              Delete
            </b-button>
          </b-col>
        </b-row>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
import moment from 'moment'

import BCard from 'bootstrap-vue/src/components/card/card'
import BCardText from 'bootstrap-vue/src/components/card/card-text'
import BCardBody from 'bootstrap-vue/src/components/card/card-body'
import BCardTitle from 'bootstrap-vue/src/components/card/card-title'
import BCardSubTitle from 'bootstrap-vue/src/components/card/card-sub-title'
import BRow from 'bootstrap-vue/src/components/layout/row'
import BCol from 'bootstrap-vue/src/components/layout/col'
import BButton from 'bootstrap-vue/src/components/button/button'

export default {
  name: 'VacancyOverviewLocal',
  components: {
    BButton,
    BCol,
    BRow,
    BCardSubTitle,
    BCardTitle,
    BCardBody,
    BCardText,
    BCard
  },
  props: {
    idx: Number,
    id: String,
    origin: String,
    datePublished: String,
    title: String,
    salary: Object,
    employer: Object
  },
  computed: {
    formatTitle () {
      return this.idx + '. ' + this.title
    },
    formatDatePublished () {
      let datePublished = this.datePublished
      if (datePublished) {
        return 'Published: ' + moment(datePublished).format('MMMM Do YYYY')
      }

      return 'Not published'
    },
    validSalary () {
      return this.salary && (this.salary.from || this.salary.to) && this.salary.currency
    },
    formatSalary () {
      if (this.salary === null) {
        return ''
      }

      console.log(this.formatSalaryRange)
      console.log(this.formatSalaryCurrency)
      return `${this.formatSalaryRange} ${this.formatSalaryCurrency}`
    },
    formatSalaryRange () {
      let from = this.salary.from
      let to = this.salary.to

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
      return this.salary.currency
    },
    validEmployer () {
      return this.employer
    },
    formatEmployer () {
      let employer = this.employer
      if (employer === null) {
        return 'Unspecified'
      }

      return employer.name
    }
  }
}
</script>

<style scoped>

</style>
