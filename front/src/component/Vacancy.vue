<template>
  <div>
    <h2>{{ title }}</h2>
    <p><em>Created: {{ dateCreated }}</em></p>
    <p><em>Published: {{ datePublished }}</em></p>
    <h3>{{ formatSalary }}</h3>
    <p>{{ description }}</p>
  </div>
</template>

<script>
export default {
  name: 'Vacancy',
  props: {
    id: Number,
    idExternal: String,
    origin: String,
    dateCreated: Date,
    datePublished: Date,
    title: String,
    description: String,
    salary: Object,
    employment: String,
    employer: Object,
    contacts: Array
  },
  computed: {
    formatSalary: function () {
      return this.salary.from + '..' + this.salary.to + ' ' + this.salary.currency
    }
  }
}
</script>

<style scoped>

</style>
