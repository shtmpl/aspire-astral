<template>
  <div>
    <b-card no-body>
      <b-card-body>
        <b-card-title>
          {{ formatTitle }}
        </b-card-title>
        <b-card-sub-title>
          <small class="text-muted"><em>{{ formatDatePublished }}</em></small>
        </b-card-sub-title>
      </b-card-body>
      <b-card-body>
        <b-card-text>
          {{ formatSalary }}
        </b-card-text>
      </b-card-body>
      <b-card-body>
        <b-row align-h="end">
          <b-col cols="auto">
            <b-button variant="outline-danger"
                      v-on:click="$emit('vacancy-delete', id)">
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
  components: { BButton, BCol, BRow, BCardSubTitle, BCardTitle, BCardBody, BCardText, BCard },
  props: {
    idx: Number,
    id: String,
    datePublished: String,
    title: String,
    salary: Object
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
    formatSalary () {
      let salary = this.salary
      if (salary === null) {
        return 'Salary: Unspecified'
      }

      return 'Salary: ' + this.formatSalaryRange(salary.from, salary.to) + ' ' + salary.currency
    }
  },
  methods: {
    formatSalaryRange (from, to) {
      if (from === null && to === null) {
        return 'Unspecified'
      } else if (from === null) {
        return 'To ' + to
      } else if (to === null) {
        return 'From ' + from
      } else if (from === to) {
        return from
      }

      return 'From ' + from + ' To ' + to
    }
  }
}
</script>

<style scoped>

</style>
