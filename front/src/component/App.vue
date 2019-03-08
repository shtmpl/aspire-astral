<template>
  <div id="root">
    <img alt="Vue logo" src="../asset/logo.png">
    <greeting msg="Greetings and Salutations!"/>
    <vacancy v-bind="vacancy"></vacancy>
  </div>
</template>

<script>
import Greeting from './Greeting'
import Vacancy from './Vacancy'

export default {
  name: 'App',
  components: {
    Greeting,
    Vacancy
  },
  data: function () {
    return {
      vacancy: {
        id: 0,
        idExternal: '0',
        origin: 'LOCAL',
        dateCreated: new Date(),
        datePublished: new Date(),
        title: 'Some title',
        description: 'Apply for this',
        salary: {
          currency: 'RUR',
          from: 0,
          to: 100
        },
        employment: 'FULL_TIME',
        employer: {
          id: 0,
          idExternal: '0',
          name: 'Company Co'
        },
        contacts: [
          {
            name: 'Me',
            email: 'me@example.com',
            phone: '79100000000'
          }
        ]
      }
    }
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
