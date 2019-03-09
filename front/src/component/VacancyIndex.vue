<template>
  <div>
    <b-input-group>
      <b-form-input placeholder="Title"
                    v-model="search.title"/>
    </b-input-group>
    <b-form-radio-group id="radio-origin"
                        buttons
                        button-variant="outline-primary"
                        v-model="search.origin"
                        v-bind:options="[{ text: 'Local', value: 'local' }, { text: 'Remote', value: 'remote' }]"/>
    <b-form-radio-group id="radio-size"
                        buttons
                        button-variant="outline-secondary"
                        v-model="search.size"
                        v-bind:options="[{ text: '10', value: 10 }, { text: '25', value: 25 }, { text: '50', value: 50 }]"/>
    <b-pagination hide-goto-end-buttons
                  limit="4"
                  v-model="search.page"
                  v-bind:total-rows="5 * search.size + 1"
                  v-bind:per-page="search.size"/>
    <div class="overflow-auto"
         v-if="loading">
      <b-spinner/>
    </div>
    <div v-else>
      <b-list-group flush>
        <b-list-group-item v-for="vacancy in vacancies"
                           v-bind:key="vacancy.id">
          <vacancy-overview v-bind="vacancy"></vacancy-overview>
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

import apiVacancy from '../api/vacancy'

import VacancyOverview from './VacancyOverview'
import BFormInput from 'bootstrap-vue/src/components/form-input/form-input'

export default {
  name: 'VacancyIndex',
  components: {
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
      search: {
        page: 1,
        size: 10,
        origin: 'local',
        title: ''
      },
      loading: false,
      vacancies: []
    }
  },
  created () {
    this.findVacanciesDebounced = _.debounce(this.findVacancies, 200)
  },
  watch: {
    search: {
      handler: function () {
        console.log('Search criteria changed')

        this.findVacanciesDebounced()
      },
      deep: true
    }
  },
  computed: {},
  methods: {
    foo (page) {
      console.log('Foo ' + page)
    },
    findVacancies () {
      let origin = this.search.origin
      let page = this.search.page - 1
      let size = this.search.size
      let title = this.search.title

      this.loading = true
      if (title) {
        apiVacancy.searchByTitle(
          {
            'title.like': title,
            origin: origin,
            page: page,
            size: size
          }
        ).then(response => {
          this.vacancies = response.data
          this.loading = false
        })
      } else {
        apiVacancy.index(
          {
            origin: origin,
            page: page,
            size: size
          }
        ).then(response => {
          this.vacancies = response.data
          this.loading = false
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
