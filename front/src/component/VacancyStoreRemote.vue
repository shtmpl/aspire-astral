<template>
  <div>
    <b-row class="mb-2">
      <b-col>
        <b-input-group>
          <b-form-input placeholder="Title"
                        v-model="search.title"/>
          <b-input-group-append>
            <b-button v-on:click="findVacanciesDebounced"
                      variant="outline-primary">
              <span v-if="searching">
                <b-spinner small/>
              </span>
              <span v-else>
                <i class="fas fa-search"></i>
              </span>
              Search
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>

    <b-row align-h="start" class="mb-2">
      <b-col cols="auto">
        <b-pagination v-model="paging.page"
                      v-bind:per-page="paging.size"
                      v-bind:total-rows="paging.total"/>
      </b-col>
      <b-col cols="auto">
        <b-input-group>
          <b-input-group-text slot="prepend">Per page</b-input-group-text>
          <b-form-select v-model="paging.size"
                         v-bind:options="[{ text: '10', value: 10 }, { text: '25', value: 25 }, { text: '50', value: 50 }]"/>
        </b-input-group>
      </b-col>
    </b-row>

    <div v-if="vacancies.length === 0">
      <em>No vacancies found</em>
    </div>
    <div v-else>
      <b-row class="mb-1"
             v-for="(vacancy, idx) in vacancies"
             v-bind:key="idx">
        <b-col>
          <vacancy-overview-remote v-bind="vacancy"
                                   v-bind:idx="pageIdx(idx)"
                                   v-bind:imported="vacancyIdsImported.includes(vacancy.id)"
                                   v-on:vacancy-import="importVacancy"></vacancy-overview-remote>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

import BPagination from 'bootstrap-vue/src/components/pagination/pagination'
import BSpinner from 'bootstrap-vue/src/components/spinner/spinner'
import BInputGroup from 'bootstrap-vue/src/components/input-group/input-group'
import BFormInput from 'bootstrap-vue/src/components/form-input/form-input'
import BInputGroupAppend from 'bootstrap-vue/src/components/input-group/input-group-append'
import BButton from 'bootstrap-vue/src/components/button/button'
import BCol from 'bootstrap-vue/src/components/layout/col'
import BRow from 'bootstrap-vue/src/components/layout/row'
import BInputGroupText from 'bootstrap-vue/src/components/input-group/input-group-text'
import BFormSelect from 'bootstrap-vue/src/components/form-select/form-select'

import ApiVacancy from '../api/vacancy'
import VacancyOverviewRemote from './VacancyOverviewRemote'

export default {
  name: 'VacancyStoreRemote',
  components: {
    BFormSelect,
    BInputGroupText,
    BRow,
    BCol,
    BButton,
    BInputGroupAppend,
    BFormInput,
    BInputGroup,
    BSpinner,
    BPagination,
    VacancyOverviewRemote
  },
  data () {
    return {
      origin: 'remote',
      vacancies: [],
      vacancyIdsImported: [],
      searching: false,
      search: {
        title: ''
      },
      paging: {
        page: 1,
        size: 10,
        total: 0
      }
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
    pageIdx (idx) {
      return (this.paging.page - 1) * this.paging.size + idx + 1
    },
    findVacancies () {
      let origin = this.origin
      let title = this.search.title
      let page = this.paging.page - 1
      let size = this.paging.size

      this.searching = true
      if (title) {
        ApiVacancy.searchVacancies(page, size, origin, title).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.slice
        }).catch(error => {
          this.$emit('error', error)
        }).finally(() => {
          this.searching = false
        })
      } else {
        ApiVacancy.indexVacancies(page, size, origin).then(response => {
          this.paging.total = response.data.total
          this.vacancies = response.data.slice
        }).catch(error => {
          this.$emit('error', error)
        }).finally(() => {
          this.searching = false
        })
      }
    },
    importVacancy (id) {
      ApiVacancy.importVacancy(this.origin, id).then(response => {
        let vacancy = response.data

        this.vacancyIdsImported.push(vacancy.id) // FIXME
        this.$emit('vacancy-add', vacancy)
      }).catch(error => {
        this.$emit('error', error)
      })
    }
  }
}
</script>

<style scoped>

</style>
