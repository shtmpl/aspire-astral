<template>
  <b-row>
    <b-col>
      {{ idx }} {{ title }}
    </b-col>
  </b-row>
</template>

<script>
import BRow from 'bootstrap-vue/src/components/layout/row'
import BCol from 'bootstrap-vue/src/components/layout/col'

export default {
  name: 'VacancyOverview',
  components: {
    BCol,
    BRow
  },
  props: {
    idx: Number,
    id: Number,
    idExternal: String,
    origin: String,
    datePublished: String,
    title: String,
    description: String,
    salary: Object,
    employment: String
  }
}
</script>

<style scoped>

</style>