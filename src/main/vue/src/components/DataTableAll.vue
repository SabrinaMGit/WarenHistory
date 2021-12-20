<template>
  <v-card>
    <v-card-title>
      Alle Event Daten
      <v-spacer></v-spacer>
    <v-text-field
      v-model="search"
      append-icon="mdi-magnify"
      label="Search"
      single-line
      hide-details
    ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="product"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
export default {
  props: ['headers', 'product', 'title'],
  data () {
    return {
      search: ''
    }
  }
}
</script>

