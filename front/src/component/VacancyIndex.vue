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
        <vacancy-store repository="local"
                       v-on:error="showError"></vacancy-store>
      </b-tab>
      <b-tab title="HeadHunter">
        <vacancy-store repository="remote"
                       v-on:error="showError"></vacancy-store>
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

import VacancyStore from './VacancyStore'

export default {
  name: 'VacancyIndex',
  components: {
    BTab,
    BTabs,
    BRow,
    BCol,
    BAlert,
    VacancyStore
  },
  data () {
    return {
      errors: []
    }
  },
  methods: {
    showError (error) {
      this.errors.push(error)
    }
  }
}
</script>

<style scoped>

</style>
