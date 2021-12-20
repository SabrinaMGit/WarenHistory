<template>
  <v-card>
    <v-card-title>
      {{ title }}
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="product"
      :search="search"
      hide-default-footer
      :items-per-page="100"
    ></v-data-table>
  </v-card>
</template>

<script>
export default {
  props: ['events', 'headers', 'product', 'title'],
  data () {
    return {
      search: ''
    }
  }
}
</script>

