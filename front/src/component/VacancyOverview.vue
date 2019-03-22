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

      <b-card-footer>
        <b-row align-h="end" no-gutters>
          <b-col cols="auto" class="mr-2">
            <b-button v-b-modal="`${repository}:${vacancy.id}:${vacancy.origin}`"
                      variant="outline-secondary">
              <span><i class="fas fa-eye"></i></span>
              View
            </b-button>
            <b-modal v-bind:id="`${repository}:${vacancy.id}:${vacancy.origin}`"
                     lazy
                     size="xl"
                     centered
                     scrollable
                     ok-only
                     ok-title="Close">
              <vacancy v-bind:repository="repository"
                       v-bind:id="vacancy.id"
                       v-bind:origin="vacancy.origin"></vacancy>
            </b-modal>
          </b-col>
          <b-col cols="auto">
            <b-button v-if="repository === 'local'" variant="outline-danger"
                      v-on:click="$emit('vacancy-delete', vacancy)">
              <span><i class="fas fa-trash"></i></span>
              Delete
            </b-button>
            <b-button v-else-if="repository === 'remote'" variant="outline-success"
                      v-on:click="$emit('vacancy-import', vacancy)">
              <span><i class="fas fa-download"></i></span>
              Import
            </b-button>
          </b-col>
        </b-row>
      </b-card-footer>
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
import BModal from 'bootstrap-vue/src/components/modal/modal'
import BCardFooter from 'bootstrap-vue/src/components/card/card-footer'

export default {
  name: 'VacancyOverview',
  components: {
    BCardFooter,
    BModal,
    BButton,
    BCol,
    BRow,
    BCardSubTitle,
    BCardTitle,
    BCardBody,
    BCardText,
    BCard,
    'vacancy': () => import('./Vacancy')
  },
  props: {
    repository: String,
    idx: Number,
    vacancy: Object
  },
  computed: {
    formatDatePublished () {
      let datePublished = this.vacancy.datePublished
      if (datePublished) {
        return `Published: ${moment(datePublished).format('MMMM Do YYYY')}`
      }

      return 'Not published'
    },
    formatTitle () {
      return this.idx + '. ' + this.vacancy.title
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
  }
}
</script>

<style scoped>

</style>
