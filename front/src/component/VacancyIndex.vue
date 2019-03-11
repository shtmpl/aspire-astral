<template>
  <div>
    <h1>Vacancies</h1>
    <b-row v-show="errors.length > 0">
      <b-col>
        <b-alert show
                 dismissible
                 variant="danger"
                 v-for="(error, idx) in errors"
                 v-bind:key="idx"
                 v-on:dismissed="errors.splice(idx, 1)">
          {{ error }}
        </b-alert>
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-form-radio-group id="radio-origin"
                            buttons
                            button-variant="outline-primary"
                            v-model="search.origin"
                            v-bind:options="[{ text: 'Local', value: 'local' }, { text: 'HeadHunter', value: 'remote' }]"/>
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-input-group>
          <b-form-input placeholder="Title"
                        v-model="search.title"/>
          <b-input-group-append>
            <b-button v-on:click="findVacanciesDebounced"
                      variant="outline-secondary">
              <span v-if="loading"><b-spinner small/></span>
              <span v-else><i class="fas fa-search"></i></span>
              Search
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>

    <b-row align-h="start">
      <b-col cols="4">
        <b-pagination v-model="paging.page"
                      v-bind:per-page="paging.size"
                      v-bind:total-rows="paging.total"/>
      </b-col>
      <b-col cols="4">
        <b-form-radio-group id="radio-size"
                            buttons
                            button-variant="outline-secondary"
                            v-model="paging.size"
                            v-bind:options="[{ text: '10', value: 10 }, { text: '25', value: 25 }, { text: '50', value: 50 }]"/>
      </b-col>
    </b-row>

    <b-row v-for="(vacancy, idx) in vacancies"
           v-bind:key="idx">
      <b-col>
        {{ (paging.page - 1) * paging.size + idx + 1 }} {{ vacancy.title }}
        <b-button v-bind:variant="isImportedVacancy(vacancy) ? 'secondary' : 'outline-success'"
                  v-on:click="importVacancy(idx, vacancy)">
          <i class="fas fa-download"></i>
          Import
        </b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import _ from 'lodash'

import BAlert from 'bootstrap-vue/src/components/alert/alert'
import BPagination from 'bootstrap-vue/src/components/pagination/pagination'
import BFormRadioGroup from 'bootstrap-vue/src/components/form-radio/form-radio-group'
import BSpinner from 'bootstrap-vue/src/components/spinner/spinner'
import BInputGroup from 'bootstrap-vue/src/components/input-group/input-group'
import BFormInput from 'bootstrap-vue/src/components/form-input/form-input'
import BInputGroupAppend from 'bootstrap-vue/src/components/input-group/input-group-append'
import BButton from 'bootstrap-vue/src/components/button/button'
import BCol from 'bootstrap-vue/src/components/layout/col'
import BRow from 'bootstrap-vue/src/components/layout/row'

import ApiVacancy from '../api/vacancy'

export default {
  name: 'VacancyIndex',
  components: {
    BRow,
    BCol,
    BAlert,
    BButton,
    BInputGroupAppend,
    BFormInput,
    BInputGroup,
    BSpinner,
    BFormRadioGroup,
    BPagination
  },
  data () {
    return {
      loading: false,
      errors: [],
      search: {
        origin: 'local',
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
        ApiVacancy.searchVacancies(page, size, origin, title).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.slice
        }).catch(error => {
          this.errors.push(error)
        }).finally(() => {
          this.loading = false
        })
      } else {
        ApiVacancy.indexVacancies(page, size, origin).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.slice
        }).catch(error => {
          this.errors.push(error)
        }).finally(() => {
          this.loading = false
        })
      }
    },
    importVacancy (idx, vacancy) {
      ApiVacancy.importVacancy(vacancy).then(response => {
        this.vacancies.splice(idx, 1, response.data)
      }).catch(error => {
        this.errors.push(error)
      })
    },
    isImportedVacancy (vacancy) {
      return vacancy.id !== null
    }
  }
}
</script>

<style scoped>

</style>
