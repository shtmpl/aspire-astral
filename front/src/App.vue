<template>
  <div id="root">
    <b-container>
      <vacancy-index/>
    </b-container>
  </div>
</template>

<script>
import BContainer from 'bootstrap-vue/src/components/layout/container'

import VacancyIndex from './component/VacancyIndex'

export default {
  name: 'App',
  components: {
    BContainer,
    VacancyIndex
  },
  data () {
    return {}
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
