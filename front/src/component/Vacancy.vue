<template>
  <div>
    <h2>{{ title }}</h2>
    <p><em>Created: {{ dateCreated }}</em></p>
    <p><em>Published: {{ datePublished }}</em></p>
    <h3>{{ formatSalary }}</h3>
    <p v-html="description"></p>
  </div>
</template>

<script>
export default {
  name: 'Vacancy',
  props: {
    id: Number,
    idExternal: String,
    origin: String,
    dateCreated: String,
    datePublished: String,
    title: String,
    description: String,
    salary: Object,
    employment: String,
    employer: Object,
    contacts: Array
  },
  computed: {
    formatSalary: function () {
      if (this.salary === null) {
        return 'ХЗ'
      } else {
        let result = ''
        if (this.salary.from !== null) {
          result += 'От ' + this.salary.from
        } else if (this.salary.to !== null) {
          result += 'До ' + this.salary.to
        }
        return result + ' ' + this.salary.currency
      }
    }
  }
}
</script>

<style scoped>

</style>
