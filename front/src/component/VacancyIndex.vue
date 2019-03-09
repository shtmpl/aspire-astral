<template>
  <div>
    <div v-show="errors.length > 0">
      <b-alert v-for="(error, idx) in errors"
               v-bind:key="idx"
               show
               dismissible
               variant="danger">
        {{ error }}
      </b-alert>
    </div>
    <b-input-group>
      <b-input-group-prepend>
        <b-form-radio-group id="radio-origin"
                            buttons
                            button-variant="outline-primary"
                            v-model="search.origin"
                            v-bind:options="[{ text: 'Local', value: 'local' }, { text: 'Remote', value: 'remote' }]"/>
      </b-input-group-prepend>
      <b-form-input placeholder="Search by title"
                    v-model="search.title"/>
      <b-input-group-append>
        <b-button v-on:click="findVacanciesDebounced"
                  variant="outline-secondary">
          <i class="fas fa-search"></i>
          Search
        </b-button>
      </b-input-group-append>
    </b-input-group>
    <b-form-radio-group id="radio-size"
                        buttons
                        button-variant="outline-secondary"
                        v-model="paging.size"
                        v-bind:options="[{ text: '10', value: 10 }, { text: '25', value: 25 }, { text: '50', value: 50 }]"/>
    <b-pagination v-model="paging.page"
                  v-bind:per-page="paging.size"
                  v-bind:total-rows="paging.total"/>
    <div class="overflow-auto"
         v-if="loading">
      <b-spinner/>
    </div>
    <div v-else>
      <b-list-group flush>
        <b-list-group-item v-for="(vacancy, idx) in vacancies"
                           v-bind:key="idx">
          <vacancy-overview v-bind="vacancy"
                            v-bind:idx="(paging.page - 1) * paging.size + idx + 1"></vacancy-overview>
        </b-list-group-item>
      </b-list-group>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

import BListGroup from 'bootstrap-vue/src/components/list-group/list-group'
import BListGroupItem from 'bootstrap-vue/src/components/list-group/list-group-item'
import BPagination from 'bootstrap-vue/src/components/pagination/pagination'
import BFormRadioGroup from 'bootstrap-vue/src/components/form-radio/form-radio-group'
import BSpinner from 'bootstrap-vue/src/components/spinner/spinner'
import BInputGroup from 'bootstrap-vue/src/components/input-group/input-group'
import BFormInput from 'bootstrap-vue/src/components/form-input/form-input'
import BInputGroupAppend from 'bootstrap-vue/src/components/input-group/input-group-append'
import BButton from 'bootstrap-vue/src/components/button/button'
import BInputGroupPrepend from 'bootstrap-vue/src/components/input-group/input-group-prepend'

import apiVacancy from '../api/vacancy'

import VacancyOverview from './VacancyOverview'
import BAlert from 'bootstrap-vue/src/components/alert/alert'

export default {
  name: 'VacancyIndex',
  components: {
    BAlert,
    BInputGroupPrepend,
    BButton,
    BInputGroupAppend,
    BFormInput,
    BInputGroup,
    BSpinner,
    BFormRadioGroup,
    BListGroup,
    BListGroupItem,
    BPagination,
    VacancyOverview
  },
  data () {
    return {
      loading: false,
      errors: [],
      search: {
        origin: 'remote',
        title: ''
      },
      paging: {
        page: 1,
        size: 10,
        total: 0
      },
      vacancies: []
    }
  },
  created () {
    this.findVacanciesDebounced = _.debounce(this.findVacancies, 200)
  },
  watch: {
    'paging.page' () {
      this.findVacanciesDebounced()
    },
    'paging.size' () {
      this.findVacanciesDebounced()
    }
  },
  methods: {
    findVacancies () {
      let origin = this.search.origin
      let title = this.search.title
      let page = this.paging.page - 1
      let size = this.paging.size

      this.loading = true
      if (title) {
        apiVacancy.searchByTitle(page, size, origin, title).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.vacancies
        }).catch(error => {
          this.errors.push(error)
        }).finally(() => {
          this.loading = false
        })
      } else {
        apiVacancy.index(page, size, origin).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.vacancies
        }).catch(error => {
          this.errors.push(error)
        }).finally(() => {
          this.loading = false
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
