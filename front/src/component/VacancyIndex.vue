<template>
  <div>
    <h1>Vacancy Store</h1>
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

    <b-tabs vertical pills>
      <b-tab title="Local"
             active>
        <vacancy-store-local v-on:error="showError"></vacancy-store-local>
      </b-tab>
      <b-tab title="HeadHunter">
        <p>Remotely fetched vacancies</p>
        <!--<vacancy-slice origin="remote"
                       v-bind:vacancies="filterRemoteVacancies"
                       v-on:vacancy-add="addVacancy"></vacancy-slice>-->
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import BAlert from 'bootstrap-vue/src/components/alert/alert'
import BCol from 'bootstrap-vue/src/components/layout/col'
import BRow from 'bootstrap-vue/src/components/layout/row'

import BTabs from 'bootstrap-vue/src/components/tabs/tabs'
import BTab from 'bootstrap-vue/src/components/tabs/tab'
import VacancyStoreLocal from './VacancyStoreLocal'

export default {
  name: 'VacancyIndex',
  components: {
    VacancyStoreLocal,
    BTab,
    BTabs,
    BRow,
    BCol,
    BAlert
  },
  data () {
    return {
      errors: [],
      vacancies: []
    }
  },
  computed: {},
  methods: {
    showError (error) {
      this.errors.push(error)
    }
  }
}
</script>

<style scoped>

</style>
