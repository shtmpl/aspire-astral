<template>
  <b-form>
    <b-input-group>
      <b-form-input placeholder="Title"
                    v-model="title"/>
      <b-input-group-append>
        <b-button v-on:click="$emit('search', title)"
                  variant="outline-secondary">
          <i class="fas fa-search"></i>
          Search
        </b-button>
      </b-input-group-append>
    </b-input-group>
  </b-form>
</template>

<script>
import BInputGroup from 'bootstrap-vue/src/components/input-group/input-group'
import BFormInput from 'bootstrap-vue/src/components/form-input/form-input'
import BInputGroupAppend from 'bootstrap-vue/src/components/input-group/input-group-append'
import BButton from 'bootstrap-vue/src/components/button/button'
import BForm from 'bootstrap-vue/src/components/form/form'

export default {
  name: 'VacancySearch',
  components: {
    BForm,
    BButton,
    BInputGroupAppend,
    BFormInput,
    BInputGroup
  },
  data () {
    return {
      title: ''
    }
  }
}
</script>

<style scoped>

</style>
